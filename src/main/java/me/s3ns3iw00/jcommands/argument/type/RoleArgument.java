package me.s3ns3iw00.jcommands.argument.type;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * An argument that only accepts role as input and returns a {@link Role}
 */
public class RoleArgument extends ValueArgument {

    public RoleArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.ROLE, Role.class);
    }

}
