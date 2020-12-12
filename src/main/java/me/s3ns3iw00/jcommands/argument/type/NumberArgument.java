package me.s3ns3iw00.jcommands.argument.type;

public class NumberArgument extends RegexArgument {

    public NumberArgument(String name) {
        super(name, "\\d+", Integer.class);
    }

}
