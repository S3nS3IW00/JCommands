# JCommands [![](https://img.shields.io/badge/Version-2.0.0-blue)](https://github.com/S3nS3IW00/JCommands) [![](https://img.shields.io/badge/Javadoc-Latest-green)](https://s3ns3iw00.github.io/JCommands/javadoc/)  
With this Javacord extension you can create commands within 1 minute that are only working in private or in the specified channels and categories with the specified roles and arguments.  There are so many useful prewritten argument types that can be used while creating a command.
  
## Code example  
The following examples might help you. Don't hesitate to use them!  
### Registering the error listener  
Error listener allows you to create custom functions for every error type.  
```java  
MessageCommandHandler.setOnError((type, cmd, sender, msg, source) -> {  
    String errorMessage = null;  
    switch (type) {  
        case INVALID_COMMAND:  
            errorMessage = "No such command! :face_with_raised_eyebrow:";  
            break;  
        case BAD_ARGUMENTS:  
            errorMessage = "Something went wrong! :face_with_raised_eyebrow: Usage: " + cmd.getUsage();  
            break;  
        case NO_PERMISSION:  
            errorMessage = "Sorry but you don't have the power to use this command! :face_with_raised_eyebrow:";  
            break;  
        case BAD_CATEGORY:  
        case BAD_CHANNEL:  
            errorMessage = "I'm not sure about that this is the best place to use this command. :face_with_raised_eyebrow:";  
            break;  
    }  
    if (msg.isPrivateMessage()) {  
        sender.sendMessage(errorMessage);  
    } else {  
        msg.getChannel().sendMessage(errorMessage);  
    }
});  
```  
  
### Creating a command  
It has never been so easy to create a command like the following example shows.  
```java  
// Create the command that is will only be available in the server
Command introduceCommand = new Command("iam", CommandType.SERVER);
// We want to make this command available in only one category
introduceCommand.setAllowedCategories(api.getChannelCategoryById(787365901552451595L).get());
// But we have two channels in that category where the command should not to work
introduceCommand.setNotAllowedChannels(api.getTextChannelById(787366035207618573L).get(), api.getTextChannelById(787366059643502644L).get());
// This command is only for users who has this role
introduceCommand.setRoles(false, api.getRoleById(787366309561368606L).get());
// Let's create our command's first argument. This argument only accepts two word separated with comma and started with capitalized letter.
introduceCommand.addArguments(new RegexArgument("Firstname,Lastname", "(?<first>[A-Z][a-z]+),(?<last>[A-Z][a-z]+)"));
// We want to know the user's gender so make an argument that has two acceptable values.
introduceCommand.addArguments(new MultiArgument("male", "female"));
// How old is the user? The user can give numbers from 0 to 99. The input will be converted into Integer.
NumberArgument ageArgument = new NumberArgument("your age (0-99)");
ageArgument.setRange(0, 99);
introduceCommand.addArguments(ageArgument);
// Let's ask the user to mention his or her best friend,
introduceCommand.addArguments(new MentionArgument("your best friend on Discord"));
// to mention his or her favourite channel on this server
introduceCommand.addArguments(new ChannelArgument("your favourite channel on this server"));
// and to send us a funny picture's url
introduceCommand.addArguments(new URLArgument("a funny picture's url"));
// Let's make an action listener where we can listen for inputs. The raw array contains the inputs from the user, and the args array contains the converted ones.
introduceCommand.setAction((sender, raw, args, msg, source) -> {
    // The RegexArgument return as a Matcher by default
    Matcher fullName = args[0].get();
    String firstName = fullName.group("first");
    String lastName = fullName.group("last");

    // The age is can be decleared as an integer without parsing because it has been converted
    int age = args[2].get();

    // You don't have to care about to fetch users and channels by their id, just call get() and let JCommands do the work for you.
    User bestFriend = args[3].get();
    Channel favouriteChannel = args[4].get();

    // URL arguments are also getting converted into the corresponding type
    URL funnyPictureUrl = args[5].get();

    // Something that uses all the arguments
    MessageBuilder responseMessage = new MessageBuilder();
    responseMessage.append("Hi " + firstName + " " + lastName + "! As I can see you are " + (age >= 10 && age < 20 ? "" : "not ") + "a teenager.");
    if (bestFriend.isBot()) {
        responseMessage.append(" What? Did you know that your best friend is a BOT?");
    }
    if(favouriteChannel.getId() == 791658134153330718L) {
        responseMessage.append(" That's my favourite channel too!");
    }
    responseMessage.append(" Lets's see your funny picture:");
    responseMessage.addAttachment(funnyPictureUrl);

    // Send a the response message to the source
    responseMessage.send(source);
});
// And don't forget to register the command on the server(s). (I always forget it and never know what's wrong :D)
MessageCommandHandler.registerCommand(introduceCommand, myServer);
```  
![Example](https://imgur.com/ciHnqpS.png)
  
> You can find a full code [here](https://github.com/S3nS3IW00/JCommands/blob/master/src/test/java/me/s3ns3iw00/jcommands/TestMain.java) what runs a bot with the example codes above.

## Wiki
You can find more information about how to use `JCommands` on the [Wiki](https://github.com/S3nS3IW00/JCommands/wiki) page.
  
## Dependencies  
As you can see in the description, this is a Javacord extension. Javacord is a framework of JDA and it so much easier to create bots with it.  
- [Javacord](https://github.com/Javacord/Javacord)
  
## Usage  
1. Download
   - [Latest release](https://github.com/S3nS3IW00/JCommands/releases/latest)
   - [![](https://jitpack.io/v/S3nS3IW00/JCommands.svg)](https://jitpack.io/#S3nS3IW00/JCommands)
   - Clone repository
   ```
   git clone https://github.com/S3nS3IW00/JCommands.git  
   ```  
2. Initiate the listener  
   ```java  
   MessageCommandHandler.setApi(DiscordApi);  
   ```  
3. Create an error listener and as many commands as you want.  
4. Enjoy!