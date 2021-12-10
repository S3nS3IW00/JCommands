/*
 * Copyright (C) 2021 S3nS3IW00
 *
 * This file is part of JCommands.
 *
 * JCommands is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser general Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * JCommands is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package me.s3ns3iw00.jcommands;

import me.s3ns3iw00.jcommands.argument.Argument;
import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.SubArgument;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;
import me.s3ns3iw00.jcommands.argument.converter.type.URLConverter;
import me.s3ns3iw00.jcommands.argument.type.ComboArgument;
import me.s3ns3iw00.jcommands.argument.type.ValueArgument;
import me.s3ns3iw00.jcommands.builder.CommandBuilder;
import me.s3ns3iw00.jcommands.builder.GlobalCommandBuilder;
import me.s3ns3iw00.jcommands.builder.PrivateCommandBuilder;
import me.s3ns3iw00.jcommands.builder.ServerCommandBuilder;
import me.s3ns3iw00.jcommands.limitation.CategoryLimitable;
import me.s3ns3iw00.jcommands.limitation.ChannelLimitable;
import me.s3ns3iw00.jcommands.listener.CommandErrorListener;
import me.s3ns3iw00.jcommands.type.GlobalCommand;
import me.s3ns3iw00.jcommands.type.PrivateCommand;
import me.s3ns3iw00.jcommands.type.ServerCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The main class of the API
 *
 * @author S3nS3IW00
 */
public class CommandHandler {

    /**
     * Some cool and obvious stuff here
     */
    private static DiscordApi api;
    private static final List<Command> commands = new ArrayList<>();
    private static final Map<Server, List<Command>> serverCommands = new HashMap<>();
    private static Optional<CommandErrorListener> error = Optional.empty();

    /**
     * HasMap that contains the converters initiated with default converters
     */
    private static final Map<Class<?>, ArgumentResultConverter> converters = new HashMap<Class<?>, ArgumentResultConverter>() {{
        put(URL.class, new URLConverter());
    }};

    /**
     * Initiates the command listener
     *
     * @param api is the instance of the Discord api
     */
    public static void setApi(DiscordApi api) {
        CommandHandler.api = api;
        api.addMessageCreateListener((event -> {
            if (event.getMessageAuthor().isBotUser()) return;
            String[] raw = event.getMessageContent().split(" ");
            if (raw[0].startsWith("/") && commands.stream().noneMatch(command -> command.getName().equalsIgnoreCase(raw[0]))) {
                error.ifPresent(e -> e.onError(CommandErrorType.INVALID_COMMAND, null));
            }
        }));

        api.addSlashCommandCreateListener(event -> {
            handleCommand(event.getSlashCommandInteraction());
        });
    }

    /**
     * Handles slash command interactions
     *
     * @param interaction the slash command interaction
     */
    private static void handleCommand(SlashCommandInteraction interaction) {
        User sender = interaction.getUser();
        Optional<TextChannel> channel = interaction.getChannel();
        Command command = commands.stream().filter(c -> c.getName().equalsIgnoreCase(interaction.getCommandName())).findFirst().get();

        //Category and channel validation
        if (channel.isPresent() && channel.get().getType() == ChannelType.SERVER_TEXT_CHANNEL) {
            Optional<ChannelCategory> category = channel.get().asServerTextChannel().get().getCategory();
            if (category.isPresent()) {
                if (command instanceof CategoryLimitable) {
                    CategoryLimitable categoryLimitable = (CategoryLimitable) command;
                    if ((categoryLimitable.isAllowedCategories() && !categoryLimitable.getCategories().contains(category.get())) || (!categoryLimitable.isAllowedCategories() && categoryLimitable.getCategories().contains(category.get()))) {
                        error.ifPresent(e -> e.onError(CommandErrorType.BAD_CATEGORY, new CommandResponder(interaction)));
                        return;
                    }
                }
            }
            if (command instanceof ChannelLimitable) {
                ChannelLimitable channelLimitable = (ChannelLimitable) command;
                if ((channelLimitable.isAllowedChannels() && !channelLimitable.getChannels().contains(channel.get())) || (!channelLimitable.isAllowedChannels() && channelLimitable.getChannels().contains(channel.get()))) {
                    error.ifPresent(e -> e.onError(CommandErrorType.BAD_CHANNEL, new CommandResponder(interaction)));
                    return;
                }
            }
        }

        //Argument validation
        Optional<List<ArgumentResult>> resultOptional = processArguments(command.getArguments(), interaction.getOptions());

        if (resultOptional.isPresent()) {
            command.getAction().ifPresent(action -> action.onCommand(interaction.getUser(), (ArgumentResult[]) resultOptional.get().toArray(),
                    new CommandResponder(interaction)));
        } else {
            error.ifPresent(e -> e.onError(CommandErrorType.BAD_ARGUMENTS, new CommandResponder(interaction)));
        }
    }

    /**
     * Processes arguments and every argument of the arguments recursively:
     * - Adjusts values to the arguments
     * - Checks that the value is valid for the argument
     * - Assembles a list with the result of every argument
     *
     * @param arguments the list of arguments need to be processed
     * @param options   the list of {@link SlashCommandInteractionOption} corresponding to {@code arguments} parameter
     * @return an {@link Optional} that is empty when one option found that is not valid for the argument during the validating process,
     *         otherwise it contains a {@link LinkedList} with {@link ArgumentResult}s in it that converts values to the final result
     */
    private static Optional<List<ArgumentResult>> processArguments(LinkedList<Argument> arguments, List<SlashCommandInteractionOption> options) {
        List<ArgumentResult> results = new ArrayList<>();
        for (SlashCommandInteractionOption option : options) {
            // Get the argument that has the same name as the option
            // It cannot be null since options are based on the registered arguments
            Argument argument = arguments.stream().filter(arg -> arg.getName().equalsIgnoreCase(option.getName())).findFirst().orElse(null);

            if (argument != null) {
                // Declare a value object that will be initiated with the value from the option
                Object value;
                switch (argument.getCommandOption().getType()) {
                    case ROLE:
                        value = option.getRoleValue().get();
                        break;
                    case USER:
                        value = option.getUserValue().get();
                        break;
                    case CHANNEL:
                        value = option.getChannelValue().get();
                        break;
                    case MENTIONABLE:
                        value = option.getMentionableValue().get();
                        break;
                    case STRING:
                        value = option.getStringValue().get();
                        break;
                    case INTEGER:
                        value = option.getIntValue().get();
                        break;
                    case BOOLEAN:
                        value = option.getBooleanValue().get();
                        break;
                    default:
                        value = null;
                }

                if (argument instanceof SubArgument) {
                    /* Add the result of the argument to the list, which is basically the name of the argument;
                       If the result is present that means there are other arguments that need to be added to the list,
                            otherwise just return an empty optional to tell the caller that there aren't any other options;
                       The best practice is to solve this recursively since the other option's count is unknown,
                            and it cannot be determined directly
                    */
                    results.add(new ArgumentResult(argument));
                    Optional<List<ArgumentResult>> result = processArguments(((SubArgument) argument).getArguments(), option.getOptions());
                    if (result.isPresent()) {
                        results.addAll(result.get());
                    } else {
                        return Optional.empty();
                    }
                } else if (argument instanceof ComboArgument) {
                    // Simply adjust the argument's value to the option's value and add it to the list
                    ((ComboArgument) argument).choose(value);
                    results.add(new ArgumentResult(argument));
                } else if (argument instanceof ValueArgument) {
                    /* Check that the option's value is valid for the argument
                       If it is valid then the validator adjust the argument's value to that value, and it will be added to the list,
                            otherwise return an empty optional to tell the caller that one of the argument is not valid
                     */
                    ValueArgument va = (ValueArgument) argument;

                    if (va.isValid(value)) {
                        results.add(new ArgumentResult(va));
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }
        return Optional.of(results);
    }

    /**
     * Registers the command for the listener on the specified servers
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     */
    public static void registerCommand(Command command, Server... servers) {
        commands.add(command);
        for (Server server : servers) {
            if (!serverCommands.containsKey(server)) {
                serverCommands.put(server, new ArrayList<>());
            }
            serverCommands.get(server).add(command);

            SlashCommand.with(command.getName(), command.getDescription(), command.getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()))
                    .createForServer(server)
                    .join();
        }
    }

    /**
     * Registers command globally
     * That means the command will be available in all the servers where the bot on, and also in private
     *
     * @param command the command to register
     */
    public static void registerCommandGlobally(Command command) {
        commands.add(command);

        SlashCommand.with(command.getName(), command.getDescription(), command.getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()))
                .createGlobal(api)
                .join();
    }

    /**
     * Registers the {@code PrivateCommand} for the listener that is will only available in private
     *
     * @param command the command
     * @deprecated since {@link PrivateCommand} is deprecated
     */
    @Deprecated
    public static void registerPrivateCommand(PrivateCommand command) {
        registerCommand(command);
    }

    /**
     * Registers the {@code ServerCommand} for the listener on the specified servers
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     * @deprecated use {@link CommandHandler#registerCommand(Command, Server...)} directly
     */
    @Deprecated
    public static void registerServerCommand(ServerCommand command, Server... servers) {
        registerCommand(command, servers);
    }

    /**
     * Registers the {@code GlobalCommand} for the listener on the specified servers and in private
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     * @deprecated since {@link GlobalCommand} is deprecated
     */
    @Deprecated
    public static void registerGlobalCommand(GlobalCommand command, Server... servers) {
        registerCommand(command, servers);
    }

    /**
     * Calls the {@link CommandHandler#registerCommand(Command, Server...)} method with the command contained by the {@code CommandBuilder} class
     *
     * @param builder the builder
     * @param servers the list of the servers where the command will be registered
     */
    public static void registerCommand(CommandBuilder builder, Server... servers) {
        registerCommand(builder.getCommand(), servers);
    }

    /**
     * Calls the {@link CommandHandler#registerCommandGlobally(Command)} method with the command contained by the {@code CommandBuilder} class
     *
     * @param builder the builder
     */
    public static void registerCommandGlobally(CommandBuilder builder) {
        registerCommandGlobally(builder.getCommand());
    }

    /**
     * Registers the {@code PrivateCommand} for the listener that is will only available in private
     *
     * @param builder the builder that contains the command
     * @deprecated since {@link PrivateCommandBuilder} is deprecated
     */
    @Deprecated
    public static void registerPrivateCommand(PrivateCommandBuilder builder) {
        registerCommand(builder);
    }

    /**
     * Registers the {@code ServerCommand} for the listener on the specified servers
     *
     * @param builder the builder that contains the command
     * @deprecated use {@link CommandHandler#registerCommand(CommandBuilder, Server...)} directly
     */
    @Deprecated
    public static void registerServerCommand(ServerCommandBuilder builder, Server... servers) {
        registerCommand(builder, servers);
    }

    /**
     * Registers the {@code GlobalCommand} for the listener on the specified servers and in private
     *
     * @param builder the builder that contains the command
     * @deprecated since {@link GlobalCommandBuilder} is deprecated
     */
    @Deprecated
    public static void registerGlobalCommand(GlobalCommandBuilder builder, Server... servers) {
        registerCommand(builder, servers);
    }

    /**
     * Registers the command on all the servers where the bot on
     *
     * @param command the command
     * @deprecated use {@link CommandHandler#registerCommandGlobally(Command)}
     */
    @Deprecated
    private static void registerCommandOnAllServer(Command command) {
        registerCommand(command, (Server[]) api.getServers().toArray());
    }

    /**
     * Registers the {@code ServerCommand} on all the servers where the bot on
     *
     * @param command the command
     * @deprecated since {@link CommandHandler#registerCommandOnAllServer(Command)} is deprecated
     */
    @Deprecated
    public static void registerServerCommandOnAllServer(ServerCommand command) {
        registerCommandOnAllServer(command);
    }

    /**
     * Registers the {@code GlobalCommand} on all the servers where the bot on and in private
     *
     * @param command the command
     * @deprecated since {@link CommandHandler#registerCommandOnAllServer(Command)} is deprecated
     */
    @Deprecated
    public static void registerGlobalCommandOnAllServer(GlobalCommand command) {
        registerCommandOnAllServer(command);
    }

    /**
     * Registers the command on all the servers where the bot on
     *
     * @param builder the builder that contains the command
     * @deprecated since {@link CommandHandler#registerCommandOnAllServer(Command)} is deprecated
     */
    @Deprecated
    private static void registerCommandOnAllServer(CommandBuilder builder) {
        registerCommandOnAllServer(builder.getCommand());
    }

    /**
     * Registers the {@code ServerCommand} on all the servers where the bot on
     *
     * @param builder the builder that contains the command
     * @deprecated since {@link CommandHandler#registerCommandOnAllServer(CommandBuilder)} is deprecated
     */
    @Deprecated
    public static void registerServerCommandOnAllServer(ServerCommandBuilder builder) {
        registerCommandOnAllServer(builder);
    }

    /**
     * Registers the {@code GlobalCommand} on all the servers where the bot on and in private
     *
     * @param builder the builder that contains the command
     * @deprecated since {@link CommandHandler#registerCommandOnAllServer(CommandBuilder)} is deprecated
     */
    @Deprecated
    public static void registerGlobalCommandOnAllServer(GlobalCommandBuilder builder) {
        registerCommandOnAllServer(builder);
    }

    /**
     * Registers an error listener where the errors will be managed
     *
     * @param error the listener interface
     */
    public static void setOnError(CommandErrorListener error) {
        CommandHandler.error = Optional.of(error);
    }

    /**
     * Sets the sign what can be determined by when a message is a command
     *
     * @param commandChar the sign that following by the command
     * @deprecated since {@code commandChar} is not exist anymore
     */
    @Deprecated
    public static void setCommandChar(String commandChar) {
    }

    /**
     * @return the list of the commands
     */
    public static List<Command> getCommands() {
        return commands;
    }

    /**
     * @param server server
     * @return the list of the commands on the specified server
     */
    public static List<Command> getCommands(Server server) {
        return serverCommands.get(server);
    }

    /**
     * Determines that a command with the given name is already exist or not
     *
     * @param cmd the command's name
     * @return exist or not
     */
    public static boolean isCommandExist(String cmd) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(cmd)) return true;
        }
        return false;
    }

    /**
     * Determines that a command with the given name is already exist or not on the specified server
     *
     * @param server server
     * @param cmd    the command's name
     * @return exist or not
     */
    public static boolean isCommandExist(Server server, String cmd) {
        for (Command command : serverCommands.get(server)) {
            if (command.getName().equalsIgnoreCase(cmd)) return true;
        }
        return false;
    }

    /**
     * Registers a converter for the given type
     *
     * @param clazz     the class of the type
     * @param converter the converter
     */
    public static void registerArgumentConverter(Class<?> clazz, ArgumentResultConverter converter) {
        converters.put(clazz, converter);
    }

    /**
     * Gets the registered converter by the type
     *
     * @param clazz the class of the type
     * @return an {@code Optional} that is empty when there is no registered converter for the given type otherwise with value of the converter
     */
    public static Optional<ArgumentResultConverter> getArgumentConverter(Class<?> clazz) {
        if (converters.containsKey(clazz)) {
            return Optional.of(converters.get(clazz));
        }
        return Optional.empty();
    }

    /**
     * @return the command sign
     * @deprecated since Discord specifies the character that the command needs to start with
     */
    @Deprecated
    public static String getCommandChar() {
        return null;
    }

    /**
     * @return the discord api
     */
    public static DiscordApi getApi() {
        return api;
    }
}