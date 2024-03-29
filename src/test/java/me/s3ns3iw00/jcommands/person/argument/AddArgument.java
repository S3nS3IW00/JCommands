package me.s3ns3iw00.jcommands.person.argument;

import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.type.ConstantArgument;
import me.s3ns3iw00.jcommands.argument.type.StringArgument;
import me.s3ns3iw00.jcommands.argument.validator.type.RegexPredicate;
import me.s3ns3iw00.jcommands.event.listener.CommandActionEventListener;
import me.s3ns3iw00.jcommands.event.type.CommandActionEvent;
import me.s3ns3iw00.jcommands.person.converter.DateConverter;
import me.s3ns3iw00.jcommands.person.model.Person;

import java.util.Date;

/**
 * A {@link ConstantArgument} that gathers data for creating new {@link me.s3ns3iw00.jcommands.person.model.Person}
 */
public class AddArgument extends ConstantArgument implements CommandActionEventListener {

    // The inputs of this argument
    private final StringArgument<String> firstNameArgument, lastNameArgument;
    private final StringArgument<Date> dateOfBirthArgument;

    /**
     * Default constructor
     */
    public AddArgument() {
        // Call super class's constructor with a name and a description
        super("add", "Adds a person to people");
        setOnAction(this); // set this class as action listener

        // First name of the person with a length validation
        firstNameArgument = new StringArgument<>("firstname", "The first name of the person, minimum 8, maximum 16 character", String.class);
        firstNameArgument.whenNotInRange(1, 16).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("Firstname's length must between 1 and 16")
                    .respond();
        });

        // Last name of the person with a length validation
        lastNameArgument = new StringArgument<>("lastname", "The last name of the person, minimum 8, maximum 16 character", String.class);
        lastNameArgument.whenNotInRange(1, 16).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("Lastname's length must between 1 and 16")
                    .respond();
        });

        // Date of birth of the person that is a STRING input with regex validation that validates by a specific date format (optional)
        dateOfBirthArgument = new StringArgument<>("dateofbirth", "The date of birth of the person (YYYY-MM-DD)", Date.class);
        dateOfBirthArgument.setOptional();
        dateOfBirthArgument.getArgumentValidator().when(RegexPredicate.notValidFor("\\d{4}-\\d{2}-\\d{2}")).thenRespond(event -> {
            event.getResponder().respondNow()
                    .setContent("Date must match to pattern YYYY-MM-DD")
                    .respond();
        });
        dateOfBirthArgument.convertResult(new DateConverter());

        // Add all the arguments to the argument
        addArgument(firstNameArgument, lastNameArgument, dateOfBirthArgument);
    }

    /**
     * The action listener
     *
     * @param event the event
     */
    @Override
    public void onAction(CommandActionEvent event) {
        ArgumentResult[] args = event.getArguments();
        CommandResponder responder = event.getResponder();

        Person person = args[0].get();

        responder.respondNow()
                .setContent(person.getFullName() + " (" + person.getAge() + ")")
                .respond();
    }

    public StringArgument<String> getFirstNameArgument() {
        return firstNameArgument;
    }

    public StringArgument<String> getLastNameArgument() {
        return lastNameArgument;
    }

    public StringArgument<Date> getDateOfBirthArgument() {
        return dateOfBirthArgument;
    }

}
