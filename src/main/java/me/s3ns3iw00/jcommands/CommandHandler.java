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
import me.s3ns3iw00.jcommands.argument.converter.ArgumentResultConverter;
import me.s3ns3iw00.jcommands.argument.converter.type.ChannelConverter;
import me.s3ns3iw00.jcommands.argument.converter.type.MentionConverter;
import me.s3ns3iw00.jcommands.argument.converter.type.URLConverter;
import me.s3ns3iw00.jcommands.builder.CommandBuilder;
import me.s3ns3iw00.jcommands.builder.GlobalCommandBuilder;
import me.s3ns3iw00.jcommands.builder.PrivateCommandBuilder;
import me.s3ns3iw00.jcommands.builder.ServerCommandBuilder;
import me.s3ns3iw00.jcommands.limitation.CategoryLimitable;
import me.s3ns3iw00.jcommands.limitation.ChannelLimitable;
import me.s3ns3iw00.jcommands.limitation.RoleLimitable;
import me.s3ns3iw00.jcommands.limitation.UserLimitable;
import me.s3ns3iw00.jcommands.type.GlobalCommand;
import me.s3ns3iw00.jcommands.type.PrivateCommand;
import me.s3ns3iw00.jcommands.type.ServerCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.net.URL;
import java.util.*;

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
    private static Optional<CommandError> error = Optional.empty();
    private static String commandChar = "/";

    /**
     * HasMap that contains the converters initiated with default converters
     */
    private static final Map<Class<?>, ArgumentResultConverter> converters = new HashMap<Class<?>, ArgumentResultConverter>() {{
        put(URL.class, new URLConverter());
        put(ServerChannel.class, new ChannelConverter());
        put(User.class, new MentionConverter());
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
            if (raw[0].startsWith(commandChar)) {
                handleCommand(event.getServer().isPresent() ? event.getServer().get() : null, event.getMessage(), raw[0].substring(commandChar.length()), raw.length > 1 ? Arrays.copyOfRange(raw, 1, raw.length) : new String[]{});
            }
        }));
    }

    /**
     * Handles the command inputs
     *
     * @param msg  the message that contains the command
     * @param cmd  the command
     * @param args the list of the command's parameters
     */
    private static void handleCommand(Server server, Message msg, String cmd, String[] args) {
        if (!msg.getAuthor().asUser().isPresent())
            return;
        User sender = msg.getAuthor().asUser().get();
        Messageable source = msg.isPrivateMessage() ? sender : msg.getChannel();

        //Command checker
        List<Command> commandList = server == null ? commands : serverCommands.get(server);
        if (commandList == null) {
            error.ifPresent(e -> e.onError(CommandErrorType.INVALID_COMMAND, null, sender, msg, source));
            return;
        }
        Command commandI = commandList.get(0);
        int cmdI = 0;
        while (cmdI < commandList.size() && (!(commandI = commandList.get(cmdI)).getName().equalsIgnoreCase(cmd))) {
            cmdI++;
        }
        if (cmdI >= commandList.size()) {
            error.ifPresent(e -> e.onError(CommandErrorType.INVALID_COMMAND, null, sender, msg, source));
            return;
        }
        final Command command = commandI;

        // User validation
        if (command instanceof UserLimitable) {
            UserLimitable userLimitable = (UserLimitable) command;
            if ((userLimitable.isAllowedUsers() && !userLimitable.getUsers().contains(sender)) || (!userLimitable.isAllowedUsers() && userLimitable.getUsers().contains(sender))) {
                error.ifPresent(e -> e.onError(CommandErrorType.NO_PERMISSION, command, sender, msg, source));
                return;
            }
        }

        //Category and channel validation
        if ((!(command instanceof GlobalCommand) && command instanceof PrivateCommand && !msg.isPrivateMessage()) || (command instanceof ServerCommand && msg.isPrivateMessage())) {
            error.ifPresent(e -> e.onError(CommandErrorType.INVALID_COMMAND, null, sender, msg, source));
            return;
        }
        if (!msg.isPrivateMessage()) {
            Optional<ServerTextChannel> serverTextChannel = api.getServerTextChannelById(msg.getChannel().getId());
            if (serverTextChannel.isPresent()) {
                Optional<ChannelCategory> category = serverTextChannel.get().getCategory();
                if (command instanceof CategoryLimitable) {
                    CategoryLimitable categoryLimitable = (CategoryLimitable) command;
                    if (category.isPresent() && ((categoryLimitable.isAllowedCategories() && !categoryLimitable.getCategories().contains(category.get())) || (!categoryLimitable.isAllowedCategories() && categoryLimitable.getCategories().contains(category.get())))
                            || (!category.isPresent() && (categoryLimitable.getCategories().size() > 0))) {
                        error.ifPresent(e -> e.onError(CommandErrorType.BAD_CATEGORY, command, sender, msg, source));
                        return;
                    }
                }
                if (command instanceof ChannelLimitable) {
                    ChannelLimitable channelLimitable = (ChannelLimitable) command;
                    if ((channelLimitable.isAllowedChannels() && !channelLimitable.getChannels().contains(serverTextChannel.get())) || (!channelLimitable.isAllowedChannels() && channelLimitable.getChannels().contains(serverTextChannel.get()))) {
                        error.ifPresent(e -> e.onError(CommandErrorType.BAD_CHANNEL, command, sender, msg, source));
                        return;
                    }
                }
            } else {
                return;
            }
        }

        //Role validation
        if (command instanceof RoleLimitable) {
            RoleLimitable roleLimitable = (RoleLimitable) command;
            Optional<Server> roleSource = Optional.empty();
            if (command instanceof PrivateCommand) {
                roleSource = ((PrivateCommand) command).getRoleSource();
            }
            if ((server != null || roleSource.isPresent()) && roleLimitable.getRoles() != null && roleLimitable.getRoles().size() > 0) {
                int needCount = roleLimitable.isNeedAllRoles() ? roleLimitable.getRoles().size() : 1;
                List<Role> userRoles = sender.getRoles(roleSource.orElse(server));

                int count = 0;
                int i = 0;
                while (i < roleLimitable.getRoles().size() && count < needCount) {
                    if (userRoles.contains(roleLimitable.getRoles().get(i))) count++;
                    i++;
                }

                if (count < needCount) {
                    error.ifPresent(e -> e.onError(CommandErrorType.NO_PERMISSION, command, sender, msg, source));
                    return;
                }
            }
        }

        //Argument validation
        ArgumentResult[] argumentResults = new ArgumentResult[command.getArguments().size()];
        if (command.getArguments() != null && command.getArguments().size() > 0) {
            boolean argsValid = true;
            if (args.length < command.getArguments().size()) {
                argsValid = false;
            } else {
                for (int i = 0; i < command.getArguments().size(); i++) {
                    List<Argument> arguments = command.getArguments().get(i);
                    String arg = args[i];
                    int j = 0;
                    while (j < arguments.size() && !arguments.get(j).isValid(arg)) {
                        j++;
                    }
                    if (j == arguments.size()) {
                        argsValid = false;
                        break;
                    } else {
                        argumentResults[i] = new ArgumentResult(arguments.get(j));
                    }
                }
            }
            if (!argsValid) {
                error.ifPresent(e -> e.onError(CommandErrorType.BAD_ARGUMENTS, command, sender, msg, source));
                return;
            }
        }

        command.getAction().ifPresent(action -> action.onCommand(sender, args, argumentResults, msg, source));
    }

    /**
     * Registers the command for the listener on the specified servers
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     */
    private static void registerCommand(Command command, Server... servers) {
        commands.add(command);
        for (Server server : servers) {
            if (!serverCommands.containsKey(server)) {
                serverCommands.put(server, new ArrayList<>());
            }
            serverCommands.get(server).add(command);
        }
    }

    /**
     * Registers the {@code PrivateCommand} for the listener that is will only available in private
     *
     * @param command the command
     */
    public static void registerPrivateCommand(PrivateCommand command) {
        registerCommand(command);
    }

    /**
     * Registers the {@code ServerCommand} for the listener on the specified servers
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     */
    public static void registerServerCommand(ServerCommand command, Server... servers) {
        registerCommand(command, servers);
    }

    /**
     * Registers the {@code GlobalCommand} for the listener on the specified servers and in private
     *
     * @param command the command
     * @param servers the list of the servers where the command will be registered
     */
    public static void registerGlobalCommand(GlobalCommand command, Server... servers) {
        registerCommand(command, servers);
    }

    /**
     * Calls the {@link CommandHandler#registerCommand(Command, Server...)} method with the command contained by the {@code CommandBuilder} class
     *
     * @param builder the builder
     * @param servers the list of the servers where the command will be registered
     */
    private static void registerCommand(CommandBuilder builder, Server... servers) {
        registerCommand(builder.getCommand(), servers);
    }

    /**
     * Registers the {@code PrivateCommand} for the listener that is will only available in private
     *
     * @param builder the builder that contains the command
     */
    public static void registerPrivateCommand(PrivateCommandBuilder builder) {
        registerCommand(builder);
    }

    /**
     * Registers the {@code ServerCommand} for the listener on the specified servers
     *
     * @param builder the builder that contains the command
     */
    public static void registerServerCommand(ServerCommandBuilder builder, Server... servers) {
        registerCommand(builder, servers);
    }

    /**
     * Registers the {@code GlobalCommand} for the listener on the specified servers and in private
     *
     * @param builder the builder that contains the command
     */
    public static void registerGlobalCommand(GlobalCommandBuilder builder, Server... servers) {
        registerCommand(builder, servers);
    }

    /**
     * Registers the command on all the servers where the bot on
     *
     * @param command the command
     */
    private static void registerCommandOnAllServer(Command command) {
        registerCommand(command, (Server[]) api.getServers().toArray());
    }

    /**
     * Registers the {@code ServerCommand} on all the servers where the bot on
     *
     * @param command the command
     */
    public static void registerServerCommandOnAllServer(ServerCommand command) {
        registerCommandOnAllServer(command);
    }

    /**
     * Registers the {@code GlobalCommand} on all the servers where the bot on and in private
     *
     * @param command the command
     */
    public static void registerGlobalCommandOnAllServer(GlobalCommand command) {
        registerCommandOnAllServer(command);
    }

    /**
     * Registers the command on all the servers where the bot on
     *
     * @param builder the builder that contains the command
     */
    private static void registerCommandOnAllServer(CommandBuilder builder) {
        registerCommandOnAllServer(builder.getCommand());
    }

    /**
     * Registers the {@code ServerCommand} on all the servers where the bot on
     *
     * @param builder the builder that contains the command
     */
    public static void registerServerCommandOnAllServer(ServerCommandBuilder builder) {
        registerCommandOnAllServer(builder);
    }

    /**
     * Registers the {@code GlobalCommand} on all the servers where the bot on and in private
     *
     * @param builder the builder that contains the command
     */
    public static void registerGlobalCommandOnAllServer(GlobalCommandBuilder builder) {
        registerCommandOnAllServer(builder);
    }

    /**
     * Registers an error listener where the errors will be managed
     *
     * @param error the listener interface
     */
    public static void setOnError(CommandError error) {
        CommandHandler.error = Optional.of(error);
    }

    /**
     * Sets the sign what can be determined by when a message is a command
     *
     * @param commandChar the sign that following by the command
     */
    public static void setCommandChar(String commandChar) {
        CommandHandler.commandChar = commandChar;
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
     */
    public static String getCommandChar() {
        return commandChar;
    }

    /**
     * @return the discord api
     */
    public static DiscordApi getApi() {
        return api;
    }
}