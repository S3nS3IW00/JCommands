package me.s3ns3iw00.jcommands.limitation;

import org.javacord.api.entity.server.Server;

/**
 * Represents a command limitation
 *
 * @param <T> aspect that the command will be limited by
 */
public abstract class Limitation<T> {

    /**
     * The server to which the limitation belongs
     */
    private final Server server;

    /**
     * It's obvious... I hope...
     */
    private final boolean permit;

    public Limitation(Server server, boolean permit) {
        this.server = server;
        this.permit = permit;
    }

    /**
     * Gets the entity of the current aspect
     *
     * @return the entity
     */
    public abstract T getEntity();

    public Server getServer() {
        return server;
    }

    public boolean isPermit() {
        return permit;
    }

}
