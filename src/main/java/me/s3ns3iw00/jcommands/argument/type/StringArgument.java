package me.s3ns3iw00.jcommands.argument.type;

public class StringArgument extends RegexArgument {

    public StringArgument(String name) {
        super(name, ".+", String.class);
    }

}
