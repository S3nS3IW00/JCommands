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
import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.argument.SubArgument;
import me.s3ns3iw00.jcommands.argument.concatenation.Concatenator;
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;
import me.s3ns3iw00.jcommands.argument.converter.type.URLConverter;
import me.s3ns3iw00.jcommands.builder.CommandBuilder;
import me.s3ns3iw00.jcommands.limitation.Limitation;
import me.s3ns3iw00.jcommands.limitation.type.CategoryLimitable;
import me.s3ns3iw00.jcommands.limitation.type.ChannelLimitable;
import me.s3ns3iw00.jcommands.limitation.type.RoleLimitable;
import me.s3ns3iw00.jcommands.limitation.type.UserLimitable;
import me.s3ns3iw00.jcommands.listener.CommandErrorListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.*;

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
    private static CommandErrorListener error;

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
        Optional<Command> commandOptional = commands.stream()
                .filter(c -> c.getName().equalsIgnoreCase(interaction.getCommandName()))
                .findFirst();

        // Check that the command needs to be handled by JCommands
        commandOptional.ifPresent(command -> {
            //Category and channel validation
            if (channel.isPresent() && channel.get().getType() == ChannelType.SERVER_TEXT_CHANNEL) {
                Optional<ChannelCategory> category = channel.get().asServerTextChannel().get().getCategory();
                if (category.isPresent()) {
                    if (command instanceof CategoryLimitable) {
                        CategoryLimitable categoryLimitable = (CategoryLimitable) command;
                        if ((categoryLimitable.getCategoryLimitations().stream().anyMatch(Limitation::isPermit) && categoryLimitable.getCategoryLimitations().stream().noneMatch(l -> l.getEntity().getId() == category.get().getId())) ||
                                (categoryLimitable.getCategoryLimitations().stream().noneMatch(Limitation::isPermit) && categoryLimitable.getCategoryLimitations().stream().anyMatch(l -> l.getEntity().getId() == category.get().getId()))) {
                            Optional.ofNullable(error).ifPresent(e -> e.onError(CommandErrorType.BAD_CATEGORY, new CommandResponder(interaction)));
                            return;
                        }
                    }
                }
                if (command instanceof ChannelLimitable) {
                    ChannelLimitable channelLimitable = (ChannelLimitable) command;
                    if ((channelLimitable.getChannelLimitations().stream().anyMatch(Limitation::isPermit) && channelLimitable.getChannelLimitations().stream().noneMatch(l -> l.getEntity().getId() == channel.get().getId())) ||
                            (channelLimitable.getChannelLimitations().stream().noneMatch(Limitation::isPermit) && channelLimitable.getChannelLimitations().stream().anyMatch(l -> l.getEntity().getId() == channel.get().getId()))) {
                        Optional.ofNullable(error).ifPresent(e -> e.onError(CommandErrorType.BAD_CHANNEL, new CommandResponder(interaction)));
                        return;
                    }
                }
            }

            //Argument validation
            Optional<Map<Argument, ArgumentResult>> resultOptional = processArguments(command.getArguments(), interaction.getOptions());

            if (resultOptional.isPresent()) {
                /* -- Argument concatenation --
                   The results list stores the result of the arguments that the concatenation process will overwrite with the concatenated values
                   The concatenated map caches the finished concatenation's process result to be able to use that in the next concatenation if present
                 */
                List<ArgumentResult> results = new LinkedList<>(resultOptional.get().values());
                Map<ArgumentResult, List<Argument>> concatenated = new LinkedHashMap<>();
                for (Concatenator concatenator : command.getConcatenators().keySet()) {
                    /* Get arguments that need to be concatenated
                       Except the optional arguments that haven't been specified
                     */
                    List<Argument> concatenatedArguments = command.getConcatenators().get(concatenator).stream()
                            .filter(arg -> !(arg instanceof InputArgument) ||
                                    !((InputArgument) arg).isOptional() ||
                                    (((InputArgument) arg).isOptional() &&
                                            resultOptional.get().containsKey(arg)))
                            .collect(Collectors.toCollection(LinkedList::new));

                    // Concatenating if the result contains the arguments in the concatenator or if the argument is optional
                    if (concatenatedArguments.stream().allMatch(arg -> resultOptional.get().containsKey(arg) ||
                            (arg instanceof InputArgument) && ((InputArgument) arg).isOptional())) {

                        /* Replaces the results with the already concatenated ones in the list that belongs to arguments that have been concatenated before
                           That means if there is multiple concatenation and the concatenator uses arguments that have been concatenated before,
                           then it should use the concatenated value instead of the arguments' value
                         */
                        List<ArgumentResult> concatenateResults = concatenatedArguments.stream()
                                .map(arg -> resultOptional.get().get(arg))
                                .collect(Collectors.toCollection(LinkedList::new));
                        for (ArgumentResult concatenatedResult : concatenated.keySet()) {
                            List<Argument> alreadyConcatenatedArguments = concatenated.get(concatenatedResult);
                            if (concatenatedArguments.containsAll(alreadyConcatenatedArguments)) {
                                alreadyConcatenatedArguments.forEach(arg -> concatenateResults.remove(resultOptional.get().get(arg)));
                                concatenateResults.add(concatenatedArguments.indexOf(alreadyConcatenatedArguments.get(0)), concatenatedResult);
                            }
                        }

                        /* Runs the concatenating process and adds its result to the results at the index of the first argument in the concatenation process
                           Replaces the concatenated arguments to the result of the concatenation
                         */
                        ArgumentResult result = new ArgumentResult(concatenator.getResultType(),
                                concatenator.concatenate(concatenateResults.toArray(new ArgumentResult[0])));
                        results.add(results.indexOf(concatenateResults.get(0)), result);
                        concatenateResults.forEach(results::remove);
                        concatenated.put(result, concatenatedArguments);
                    }
                }

                command.getAction().ifPresent(action -> action.onCommand(interaction.getUser(), results.toArray(new ArgumentResult[]{}),
                        new CommandResponder(interaction)));
            } else {
                Optional.ofNullable(error).ifPresent(e -> e.onError(CommandErrorType.BAD_ARGUMENTS, new CommandResponder(interaction)));
            }
        });
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
     * otherwise it contains a {@link LinkedHashMap} with {@link Argument}s and their {@link ArgumentResult} in it that converts values to the final result
     */
    private static Optional<Map<Argument, ArgumentResult>> processArguments(List<Argument> arguments, List<SlashCommandInteractionOption> options) {
        Map<Argument, ArgumentResult> results = new LinkedHashMap<>();
        for (Argument argument : arguments) {
            // Get the argument that has the same name as the option;
            // Option is null when the argument is marked as optional, and was not specified
            SlashCommandInteractionOption option = options.stream().filter(opt -> opt.getName().equalsIgnoreCase(argument.getName())).findFirst().orElse(null);

            if (option != null) {
                Object value = getOptionValue(option, argument.getType());

                if (argument instanceof SubArgument) {
                    /* Add the result of the argument to the list, which is basically the name of the argument;
                       If the result is present that means there are other arguments that need to be added to the list,
                            otherwise just return an empty optional to tell the caller that there aren't any other options;
                       The best practice is to solve this recursively since the other option's count is unknown,
                            and it cannot be determined directly
                    */
                    results.put(argument, new ArgumentResult(argument));
                    Optional<Map<Argument, ArgumentResult>> result = processArguments(((SubArgument) argument).getArguments(), option.getOptions());
                    if (result.isPresent()) {
                        results.putAll(result.get());
                    } else {
                        return Optional.empty();
                    }
                } else if (argument instanceof InputArgument) {
                    /* Adjusts the value to the argument and checks that the value is null
                       If it is not then it will be added to the list,
                            otherwise return an empty optional to tell the caller that one of the argument is not valid
                     */
                    InputArgument ia = (InputArgument) argument;
                    ia.input(value);

                    if (ia.getValue() != null) {
                        results.put(argument, new ArgumentResult(ia));
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }
        return Optional.of(results);
    }

    /**
     * Gets the value of the option based on its type
     *
     * @param option the option
     * @param type   the requested type
     * @return the value of the option based on its type
     */
    private static Object getOptionValue(SlashCommandInteractionOption option, SlashCommandOptionType type) {
        Object value;
        switch (type) {
            case ROLE:
                value = option.getRoleValue().orElse(null);
                break;
            case USER:
                value = option.getUserValue().orElse(null);
                break;
            case CHANNEL:
                value = option.getChannelValue().orElse(null);
                break;
            case MENTIONABLE:
                value = option.getMentionableValue().orElse(null);
                break;
            case STRING:
                value = option.getStringValue().orElse(null);
                break;
            case INTEGER:
                value = option.getIntValue().orElse(null);
                break;
            case BOOLEAN:
                value = option.getBooleanValue().orElse(null);
                break;
            default:
                value = null;
        }
        return value;
    }

    /**
     * Registers the command for the listener on the specified servers
     * and sets up permissions with discord's default permission system
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     */
    public static void registerCommand(Command command, Server... servers) {
        commands.add(command);

        /*
          Check if default permissions need to be turned off
          They will get turned off when user or role limitation has been set on a command
        */
        boolean defPermissions = !((command instanceof UserLimitable && ((UserLimitable) command).getUserLimitations().stream().anyMatch(Limitation::isPermit)) ||
                (command instanceof RoleLimitable && ((RoleLimitable) command).getRoleLimitations().stream().anyMatch(Limitation::isPermit)));

        for (Server server : servers) {
            if (!serverCommands.containsKey(server)) {
                serverCommands.put(server, new ArrayList<>());
            }
            serverCommands.get(server).add(command);

            long id = SlashCommand.with(command.getName(), command.getDescription(), command.getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()))
                    .setDefaultPermission(defPermissions)
                    .createForServer(server)
                    .join()
                    .getId();

            applyPermissions(command, id, server);
        }
    }

    /**
     * Registers command globally
     * That means the command will be available in all the servers where the bot on, and also in private
     * Sets up permissions with discord's default permission system
     *
     * @param command the command to register
     */
    public static void registerCommand(Command command) {
        commands.add(command);

        /*
          Check if default permissions need to be turned off
          They will get turned off when user or role limitation has been set on a command
        */
        boolean defPermissions = !((command instanceof UserLimitable && ((UserLimitable) command).getUserLimitations().stream().anyMatch(Limitation::isPermit)) ||
                (command instanceof RoleLimitable && ((RoleLimitable) command).getRoleLimitations().stream().anyMatch(Limitation::isPermit)));

        long id = SlashCommand.with(command.getName(), command.getDescription(), command.getArguments().stream().map(Argument::getCommandOption).collect(Collectors.toList()))
                .setDefaultPermission(defPermissions)
                .createGlobal(api)
                .join()
                .getId();

        for (Server server : api.getServers()) {
            applyPermissions(command, id, server);
        }
    }

    /**
     * Applies default Discord permissions on command
     *
     * @param command the command
     * @param id      the slash command's id
     * @param server  the server
     */
    private static void applyPermissions(Command command, long id, Server server) {
        List<SlashCommandPermissions> permissions = new ArrayList<>();
        if (command instanceof UserLimitable) {
            UserLimitable userLimitable = (UserLimitable) command;
            permissions.addAll(userLimitable.getUserLimitations().stream()
                    .filter(user -> user.getServer().getId() == server.getId())
                    .map(user -> SlashCommandPermissions.create(user.getEntity().getId(),
                            SlashCommandPermissionType.USER,
                            user.isPermit()))
                    .collect(Collectors.toList()));
        }
        if (command instanceof RoleLimitable) {
            RoleLimitable roleLimitable = (RoleLimitable) command;
            permissions.addAll(roleLimitable.getRoleLimitations().stream()
                    .filter(user -> user.getServer().getId() == server.getId())
                    .map(role -> SlashCommandPermissions.create(role.getEntity().getId(),
                            SlashCommandPermissionType.ROLE,
                            role.isPermit()))
                    .collect(Collectors.toList()));
        }

        if (permissions.size() > 0) {
            new SlashCommandPermissionsUpdater(server)
                    .setPermissions(permissions)
                    .update(id)
                    .join();
        }
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
     * Calls the {@link CommandHandler#registerCommand(Command)} method with the command contained by the {@code CommandBuilder} class
     *
     * @param builder the builder
     */
    public static void registerCommand(CommandBuilder builder) {
        registerCommand(builder.getCommand());
    }

    /**
     * Registers an error listener where the errors will be managed
     *
     * @param error the listener interface
     */
    public static void setOnError(CommandErrorListener error) {
        CommandHandler.error = error;
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
     * @return the discord api
     */
    public static DiscordApi getApi() {
        return api;
    }
}