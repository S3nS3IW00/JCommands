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
import me.s3ns3iw00.jcommands.argument.type.RegexArgument;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A simple class filled with valuable code
 *
 * @author S3nS3IW00
 */
public class MessageCommandHandler {

    /**
     * Some cool and obvious stuff here
     */
    private static Server server;
    private static List<Command> commands = new ArrayList<>();
    private static CommandError error;
    private static String commandChar = "/";

    /**
     * Initiates the command listener
     *
     * @param server is the instance of the current server
     */
    public static void setServer(Server server) {
        MessageCommandHandler.server = server;
        server.getApi().addMessageCreateListener((event -> {
            if (event.getMessageAuthor().isBotUser()) return;
            String[] raw = event.getMessageContent().split(" ");
            if (raw[0].startsWith(commandChar)) {
                handleCommand(event.getMessage(), raw[0].substring(commandChar.length()), raw.length > 1 ? Arrays.copyOfRange(raw, 1, raw.length) : new String[]{});
            }
        }));
    }

    /**
     * Handles the command inputs
     *
     * @param msg the message what contains the command
     * @param cmd the command
     * @param args the list of the parameters of the command
     */
    private static void handleCommand(Message msg, String cmd, String[] args) {
        if (!msg.getAuthor().asUser().isPresent()) return;
        User sender = msg.getAuthor().asUser().get();
        Command commandI = commands.get(0);
        int cmdI = 0;
        while (cmdI < commands.size() && !(commandI = commands.get(cmdI)).getName().equalsIgnoreCase(cmd)) {
            cmdI++;
        }
        if (cmdI >= commands.size()) {
            if(error != null) error.onError(CommandErrorType.INVALID_COMMAND, null, sender, msg);
            return;
        }
        final Command command = commandI;

        //Category and channel validation
        if (command.getType() == CommandType.PM && !msg.isPrivateMessage()) {
            return;
        } else if (command.getType() == CommandType.BOTH || command.getType() == CommandType.SERVER) {
            if (!msg.isPrivateMessage()) {
                Optional<ServerTextChannel> serverTextChannel = server.getApi().getServerTextChannelById(msg.getChannel().getId());
                if (serverTextChannel.isPresent()) {
                    Optional<ChannelCategory> category = serverTextChannel.get().getCategory();
                    if (category.isPresent()) {
                        if (command.getNotAllowedCategories().contains(category.get()) || (command.getAllowedCategories().size() > 0 && !command.getAllowedCategories().contains(category.get()))) {
                            if(error != null) error.onError(CommandErrorType.BAD_CATEGORY, command, sender, msg);
                            return;
                        } else {
                            if (command.getNotAllowed().contains(msg.getChannel()) || (command.getAllowed().size() > 0 && !command.getAllowed().contains(msg.getChannel()))) {
                                if(error != null) error.onError(CommandErrorType.BAD_CHANNEL, command, sender, msg);
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
        }

        //Role validation
        if (command.getRoles() != null && command.getRoles().length > 0) {
            int needCount = command.isNeedAllRole() ? command.getRoles().length : 1;
            List<Role> userRoles = sender.getRoles(server);

            int count = 0;
            int i = 0;
            while (i < command.getRoles().length && count < needCount) {
                if (userRoles.contains(command.getRoles()[i])) count++;
                i++;
            }

            if (count < needCount) {
                if(error != null) error.onError(CommandErrorType.NO_PERMISSION, command, sender, msg);
                return;
            }
        }

        //Argument validation
        Argument[] appliedArguments = new Argument[command.getArguments().size()];
        if (command.getArguments() != null && command.getArguments().size() > 0) {
            boolean argsValid = true;
            if (args.length < command.getArguments().size()) {
                argsValid = false;
            } else {
                for (int i = 0; i < command.getArguments().size(); i++) {
                    List<Argument> arguments = command.getArguments().get(i);
                    String arg = args[i];
                    int j = 0;
                    while (j < arguments.size() && ((!(arguments.get(j) instanceof RegexArgument) && !arguments.get(j).getName().equalsIgnoreCase(arg)) || ((arguments.get(j) instanceof RegexArgument) && !((RegexArgument) arguments.get(j)).validate(arg).matches()))) {
                        j++;
                    }
                    if (j == arguments.size()) {
                        argsValid = false;
                        break;
                    } else {
                        appliedArguments[i] = arguments.get(j);
                    }
                }
            }
            if (!argsValid) {
                if(error != null) error.onError(CommandErrorType.BAD_ARGUMENTS, command, sender, msg);
                return;
            }
        }

        if (command.getAction() != null) command.getAction().onCommand(sender, args, appliedArguments, msg);
    }

    /**
     * Registers the command for the listener
     *
     * @param command the command
     */
    public static void registerCommand(Command command) {
        commands.add(command);
    }

    /**
     * Registers an error listener where the errors will be managed
     *
     * @param error the listener interface
     */
    public static void setOnError(CommandError error) {
        MessageCommandHandler.error = error;
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
     *
     * @return the list of the commands
     */
    public static List<Command> getCommands() {
        return commands;
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
     *
     * @return the command sign
     */
    public static String getCommandChar() {
        return commandChar;
    }
}