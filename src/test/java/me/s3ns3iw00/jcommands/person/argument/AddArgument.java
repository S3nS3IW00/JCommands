package me.s3ns3iw00.jcommands.person.argument;

import me.s3ns3iw00.jcommands.argument.type.ConstantArgument;
import me.s3ns3iw00.jcommands.argument.type.StringArgument;
import me.s3ns3iw00.jcommands.argument.type.ValueArgument;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Date;

/**
 * A {@link ConstantArgument} that gathers data for creating new {@link me.s3ns3iw00.jcommands.person.model.Person}
 */
public class AddArgument extends ConstantArgument {

    // The inputs of this argument
    private final StringArgument firstNameArgument, lastNameArgument;
    private final ValueArgument dateOfBirthArgument;

    /**
     * Default constructor
     */
    public AddArgument() {
        // Call super class's constructor with a name and a description
        super("add", "Adds a person to people");

        // First name of the person with a maximum length validation
        firstNameArgument = new StringArgument("firstname", "The first name of the person, maximum 16 character");
        firstNameArgument.setMaxLength(16);
        firstNameArgument.setOnMismatch(event -> {
            event.getResponder().respondNow()
                    .setContent("Firstname's maximum length is 16 characters")
                    .respond();
        });

        // Last name of the person with a maximum length validation
        lastNameArgument = new StringArgument("lastname", "The last name of the person, maximum 16 character");
        lastNameArgument.setMaxLength(16);
        lastNameArgument.setOnMismatch(event -> {
            event.getResponder().respondNow()
                    .setContent("Lastname's maximum length is 16 characters")
                    .respond();
        });

        // Date of birth of the person that is a STRING input with regex validation that validates by a specific date format (optional)
        dateOfBirthArgument = new ValueArgument("dateofbirth", "The date of birth of the person (YYYY-MM-DD)", SlashCommandOptionType.STRING, Date.class);
        dateOfBirthArgument.validate("\\d{4}-\\d{2}-\\d{2}");
        dateOfBirthArgument.setOptional();
        dateOfBirthArgument.setOnMismatch(event -> {
            event.getResponder().respondNow()
                    .setContent("Date must match to pattern YYYY-MM-DD")
                    .respond();
        });

        // Add all the arguments to the argument
        addArgument(firstNameArgument, lastNameArgument, dateOfBirthArgument);
    }

    public StringArgument getFirstNameArgument() {
        return firstNameArgument;
    }

    public StringArgument getLastNameArgument() {
        return lastNameArgument;
    }

    public ValueArgument getDateOfBirthArgument() {
        return dateOfBirthArgument;
    }

}
