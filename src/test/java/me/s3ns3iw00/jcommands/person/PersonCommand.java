package me.s3ns3iw00.jcommands.person;

import me.s3ns3iw00.jcommands.CommandResponder;
import me.s3ns3iw00.jcommands.argument.ArgumentResult;
import me.s3ns3iw00.jcommands.argument.concatenation.type.StringConcatenator;
import me.s3ns3iw00.jcommands.argument.concatenation.type.TypeConcatenator;
import me.s3ns3iw00.jcommands.event.listener.CommandActionEventListener;
import me.s3ns3iw00.jcommands.event.type.CommandActionEvent;
import me.s3ns3iw00.jcommands.person.argument.AddArgument;
import me.s3ns3iw00.jcommands.person.model.Person;
import me.s3ns3iw00.jcommands.type.ServerCommand;

/**
 * A {@link me.s3ns3iw00.jcommands.type.ServerCommand} to manage people, and a {@link CommandActionEventListener} to be able to listen for its usage
 */
public class PersonCommand extends ServerCommand implements CommandActionEventListener {

    /**
     * Default constructor
     */
    public PersonCommand() {
        // Call super class's constructor with a name and a description
        super("person", "A command to manage people");
        setOnAction(this); // set this class as action listener

        // Instantiate the sub command and adds it to the command
        AddArgument addArgument = new AddArgument();
        addArgument(addArgument);

        // Add a concatenator to the command that concatenates the two parts of the name from the sub command into one String separated with a space
        // This is needed because Person takes the name as one piece
        addConcatenator(new StringConcatenator<>(" ", String.class), addArgument.getFirstNameArgument(), addArgument.getLastNameArgument());

        // Add another concatenator that concatenates all the arguments in the sub command into an instance of Person
        // The parameters' type, count and order is matter because it needs to match with one of the constructor of Person
        // This could be wrong because there is no constructor that takes two String and a Date type of parameter but the first two parameters will be replaced by the result of the concatenator above,
        // so it will be a String and a Date type of parameter and there is a constructor that takes these parameters
        addConcatenator(new TypeConcatenator<>(Person.class), addArgument.getFirstNameArgument(), addArgument.getLastNameArgument(), addArgument.getDateOfBirthArgument());
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

        String action = args[0].get();
        if (action.equalsIgnoreCase("add")) {
            Person person = args[1].get();

            responder.respondNow()
                    .setContent(person.getFullName() + " (" + person.getAge() + ")")
                    .respond();
        }
    }

}
