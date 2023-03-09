package me.s3ns3iw00.jcommands.builder.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.builder.CommandBuilder;
import me.s3ns3iw00.jcommands.type.ServerCommand;

/**
 * Useful class that makes {@link ServerCommand} creations more comfortable
 */
public class ServerCommandBuilder extends CommandBuilder<ServerCommandBuilder> {

    private final ServerCommand command;

    public ServerCommandBuilder(String name, String description) {
        command = new ServerCommand(name, description);
    }

    @Override
    public Command getCommand() {
        return command;
    }

}