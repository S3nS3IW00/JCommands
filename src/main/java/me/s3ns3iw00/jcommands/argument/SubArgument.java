package me.s3ns3iw00.jcommands.argument;

import java.util.LinkedList;

/**
 * Represents a sub argument
 * Classes that extends this class can have arguments
 */
public abstract class SubArgument extends Argument {

    public abstract LinkedList<Argument> getArguments();

}
