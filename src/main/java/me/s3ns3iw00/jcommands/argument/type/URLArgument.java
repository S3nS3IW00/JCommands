package me.s3ns3iw00.jcommands.argument.type;

public class URLArgument extends RegexArgument {

    public URLArgument(String name) {
        super(name, "(http(s)?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)");
    }

}
