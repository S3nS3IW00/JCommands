package me.s3ns3iw00.jcommands.builder.type;

import me.s3ns3iw00.jcommands.Command;
import me.s3ns3iw00.jcommands.builder.CommandBuilder;
import me.s3ns3iw00.jcommands.type.GlobalCommand;

/**
 * Useful class that makes {@link GlobalCommand} creations more comfortable
 */
public class GlobalCommandBuilder extends CommandBuilder<GlobalCommandBuilder> {

    private final GlobalCommand command;

    public GlobalCommandBuilder(String name, String description) {
        command = new GlobalCommand(name, description);
    }

    public GlobalCommandBuilder enableInDMs() {
        command.enableInDMs();
        return this;
    }

    public GlobalCommandBuilder disableInDMs() {
        command.disableInDMs();
        return this;
    }

    @Override
    public Command getCommand() {
        return command;
    }

}
