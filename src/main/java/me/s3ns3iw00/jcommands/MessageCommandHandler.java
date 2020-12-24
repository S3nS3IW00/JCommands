/*
 * Copyright (C) 2020 S3nS3IW00
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
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.*;

/**
 * The main class of the API
 *
 * @author S3nS3IW00
 */
public class MessageCommandHandler {

    /**
     * Some cool and obvious stuff here
     */
    private static DiscordApi api;
    private static List<Command> commands = new ArrayList<>();
    private static Map<Server, List<Command>> serverCommands = new HashMap<>();
    private static Optional<CommandError> error = Optional.empty();
    private static String commandChar = "/";

    /**
     * Initiates the command listener
     *
     * @param api is the instance of the Discord api
     */
    public static void setApi(DiscordApi api) {
        MessageCommandHandler.api = api;
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
     * @param msg  the message what contains the command
     * @param cmd  the command
     * @param args the list of the command's parameters
     */
    private static void handleCommand(Server server, Message msg, String cmd, String[] args) {
        if (!msg.getAuthor().asUser().isPresent())
            return;
        User sender = msg.getAuthor().asUser().get();

        //Command checker
        List<Command> commandList = server == null ? commands : serverCommands.get(server);
        if (commandList == null) {
            error.ifPresent(e -> e.onError(CommandErrorType.INVALID_COMMAND, null, sender, msg));
            return;
        }
        Command commandI = commandList.get(0);
        int cmdI = 0;
        while (cmdI < commandList.size() && (!(commandI = commandList.get(cmdI)).getName().equalsIgnoreCase(cmd))) {
            cmdI++;
        }
        if (cmdI >= commandList.size()) {
            error.ifPresent(e -> e.onError(CommandErrorType.INVALID_COMMAND, null, sender, msg));
            return;
        }
        final Command command = commandI;

        // User validation
        if (command.getNotAllowedUserList().contains(sender) || (command.getAllowedUserList().size() > 0 && !command.getAllowedUserList().contains(sender))) {
            error.ifPresent(e -> e.onError(CommandErrorType.NO_PERMISSION, command, sender, msg));
            return;
        }

        //Category and channel validation
        if ((command.getType() == CommandType.PM && !msg.isPrivateMessage()) || (command.getType() == CommandType.SERVER && msg.isPrivateMessage())) {
            return;
        }
        if (!msg.isPrivateMessage()) {
            Optional<ServerTextChannel> serverTextChannel = api.getServerTextChannelById(msg.getChannel().getId());
            if (serverTextChannel.isPresent()) {
                Optional<ChannelCategory> category = serverTextChannel.get().getCategory();
                if (category.isPresent()) {
                    if (command.getNotAllowedCategoryList().contains(category.get()) || (command.getAllowedCategoryList().size() > 0 && !command.getAllowedCategoryList().contains(category.get()))) {
                        error.ifPresent(e -> e.onError(CommandErrorType.BAD_CATEGORY, command, sender, msg));
                        return;
                    } else {
                        if (command.getNotAllowedChannelList().contains(msg.getChannel()) || (command.getAllowedChannelList().size() > 0 && !command.getAllowedChannelList().contains(msg.getChannel()))) {
                            error.ifPresent(e -> e.onError(CommandErrorType.BAD_CHANNEL, command, sender, msg));
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }

        //Role validation
        if ((server != null || command.getRoleSource().isPresent()) && command.getRoles() != null && command.getRoles().length > 0) {
            int needCount = command.isNeedAllRole() ? command.getRoles().length : 1;
            List<Role> userRoles = sender.getRoles(command.getRoleSource().orElse(server));

            int count = 0;
            int i = 0;
            while (i < command.getRoles().length && count < needCount) {
                if (userRoles.contains(command.getRoles()[i])) count++;
                i++;
            }

            if (count < needCount) {
                error.ifPresent(e -> e.onError(CommandErrorType.NO_PERMISSION, command, sender, msg));
                return;
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
                error.ifPresent(e -> e.onError(CommandErrorType.BAD_ARGUMENTS, command, sender, msg));
                return;
            }
        }

        command.getAction().ifPresent(action -> action.onCommand(sender, args, argumentResults, msg, msg.isPrivateMessage() ? sender : msg.getChannel()));
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
        }
    }

    /**
     * Registers an error listener where the errors will be managed
     *
     * @param error the listener interface
     */
    public static void setOnError(CommandError error) {
        MessageCommandHandler.error = Optional.of(error);
    }

    /**
     * Sets the sign what can be determined by when a message is a command
     *
     * @param commandChar the sign that following by the command
     */
    public static void setCommandChar(String commandChar) {
        MessageCommandHandler.commandChar = commandChar;
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
     * @return the command sign
     */
    public static String getCommandChar() {
        return commandChar;
    }

    public static DiscordApi getApi() {
        return api;
    }
}