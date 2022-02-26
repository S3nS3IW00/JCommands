package me.s3ns3iw00.jcommands.argument.util;

import org.javacord.api.interaction.SlashCommandOptionChoice;

/**
 * A helper data class that stores data for creating a {@link SlashCommandOptionChoice}
 */
public class Choice {

    private final String key;
    private final Object value;

    /**
     * Default constructor
     *
     * @param key   the choice's key (the user will see this)
     * @param value the choice's value
     *              can be {@link String} or {@link Long}
     */
    public Choice(String key, Object value) {
        this.key = key;
        this.value = value;

        if (value.getClass() != String.class && value.getClass() != Long.class) {
            throw new IllegalArgumentException("Value must be String or long");
        }
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    /**
     * Constructs the {@link SlashCommandOptionChoice} instance
     *
     * @return the {@link SlashCommandOptionChoice}
     */
    public SlashCommandOptionChoice getChoice() {
        if (value.getClass() == String.class) {
            return SlashCommandOptionChoice.create(key, (String) value);
        } else if (value.getClass() == Long.class) {
            return SlashCommandOptionChoice.create(key, (long) value);
        }
        return null;
    }
}
