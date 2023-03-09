package me.s3ns3iw00.jcommands.type;

import me.s3ns3iw00.jcommands.Command;

/**
 * A {@link Command} that will be registered on all the servers where the bot on and also can be available in dms
 */
public class GlobalCommand extends Command {

    private boolean enabledInDMs = true;

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
    public GlobalCommand(String name, String description) {
        super(name, description);
    }

    /**
     * Disables the command in DMs
     */
    public void disableInDMs() {
        enabledInDMs = false;
    }

    /**
     * Enables the command in DMs
     */
    public void enableInDMs() {
        enabledInDMs = true;
    }

    /**
     * Checks if the command is enabled in DMs
     *
     * @return true if the command is enabled in DMs, false otherwise
     */
    public boolean isEnabledInDMs() {
        return enabledInDMs;
    }
}
