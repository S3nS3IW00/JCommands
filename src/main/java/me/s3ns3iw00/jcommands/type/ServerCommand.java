package me.s3ns3iw00.jcommands.type;

import me.s3ns3iw00.jcommands.Command;

/**
 * A {@link Command} that only can be registered on servers
 */
public class ServerCommand extends Command {

    /**
     * Default constructor
     *
     * @param name        the name of the command
     *                    Its length must between 1 and 32
     *                    Can contain only:
     *                    - word characters
     *                    - numbers
     *                    - '-' characters
     * @param description the description of the command
     *                    Its length must between 1 and 100
     */
    public ServerCommand(String name, String description) {
        super(name, description);
    }

}
