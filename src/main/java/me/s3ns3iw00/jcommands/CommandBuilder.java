package me.s3ns3iw00.jcommands;

import me.s3ns3iw00.jcommands.argument.Argument;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class CommandBuilder extends Command {

    public CommandBuilder(String name, CommandType type) {
        super(name, type);
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#addArguments(Argument...) addArguments}
     *
     * @return this class
     */
    public Command arguments(Argument... arguments) {
        addArguments(arguments);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setAllowedUsers(User...)} setAllowedUsers}
     *
     * @return this class
     */
    public Command allowedUsers(User... users) {
        setAllowedUsers(users);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setNotAllowedUsers(User...)} setNotAllowedUsers}
     *
     * @return this class
     */
    public Command notAllowedUsers(User... users) {
        setNotAllowedUsers(users);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setAllowedCategories(ChannelCategory...)} setAllowedCategories}
     *
     * @return this class
     */
    public Command allowedCategories(ChannelCategory... categories) {
        setAllowedCategories(categories);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setNotAllowedCategories(ChannelCategory...)} setNotAllowedCategories}
     *
     * @return this class
     */
    public Command notAllowedCategories(ChannelCategory... categories) {
        setNotAllowedCategories(categories);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setAllowedChannels(TextChannel...)} setAllowedChannels}
     *
     * @return this class
     */
    public Command allowedChannels(TextChannel... channels) {
        setAllowedChannels(channels);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setNotAllowedChannels(TextChannel...)} setNotAllowedChannels}
     *
     * @return this class
     */
    public Command notAllowedChannels(TextChannel... channels) {
        setNotAllowedChannels(channels);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setRoles(boolean, Role...)} setRoles}
     *
     * @return this class
     */
    public Command roles(boolean needAllRole, Role... roles) {
        setRoles(needAllRole, roles);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setRoleSource(Server)} setRoleSource}
     *
     * @return this class
     */
    public Command roleSource(Server server) {
        setRoleSource(server);
        return this;
    }

    /**
     * Extends {@link me.s3ns3iw00.jcommands.Command#setAction(CommandAction)} setAction}
     *
     * @return this class
     */
    public Command action(CommandAction action) {
        setAction(action);
        return this;
    }

}
