# JCommands [![](https://img.shields.io/badge/Version-4.0.1-blue)](https://github.com/S3nS3IW00/JCommands) [![](https://img.shields.io/badge/Javadoc-Latest-green)](https://s3ns3iw00.github.io/JCommands/javadoc/)

With this Javacord extension you can create Slash commands within 1 minute with built-in validating, converting and
extended permission checking for channels and categories. There are so many useful pre-written argument types that can
be used while creating a command.

## Code example

The following examples might help you. Don't hesitate to use them!

### Registering the error listener

Error listener allows you to create custom functions for every error type.

```java  
CommandHandler.setOnError((type, responder) -> {
   String errorMessage = null;
   switch (type) {
       // Occurs when one or more of the arguments are missing or not matching the pattern.
       case BAD_ARGUMENTS:
           errorMessage = "Something went wrong! :face_with_raised_eyebrow:";
           break;
       // Occurs when the sender wants to use the command in a category where it is not allowed.
       case BAD_CATEGORY:
           // Occurs when the sender wants to use the command in a channel where it is not allowed.
       case BAD_CHANNEL:
           errorMessage = "I'm not sure about that this is the best place to use this command. :face_with_raised_eyebrow:";
           break;
   }
   responder.respondNow()
           .setContent(errorMessage)
           .respond();
});
```  
  
### Creating a command  
It has never been so easy to create a command like the following example shows.

```java  
// Create a command with a name and a description
ServerCommand introduceCommand = new ServerCommand("iam", "This command is for to introduce yourself.");
// We want to make this command available in only one category
introduceCommand.addCategoryLimitation(CategoryLimitation.with(myServer, true, api.getChannelCategoryById(787365901552451595L).get()));
// But we have two channels in that category where the command should not to work
introduceCommand.addChannelLimitation(ChannelLimitation.with(myServer, false, api.getTextChannelById(787366035207618573L).get()));
introduceCommand.addChannelLimitation(ChannelLimitation.with(myServer, false, api.getTextChannelById(787366059643502644L).get()));
// This command is only for users who have this role
introduceCommand.addRoleLimitation(RoleLimitation.with(myServer, true, api.getRoleById(787366309561368606L).get()));
// Let's create our command's first argument.
ValueArgument nameArgument = new ValueArgument("fullname", "Your first and last name separated with comma (Firstname,Lastname)", SlashCommandOptionType.STRING);
// This argument only accepts two word separated with comma and each word started with capitalized letter.
nameArgument.validate("(?<first>[A-Z][a-z]+),(?<last>[A-Z][a-z]+)");
// We want to know the user's gender so make an argument that has two acceptable values.
ComboArgument genderArgument = new ComboArgument("gender", "Your gender", SlashCommandOptionType.INTEGER);
genderArgument.addChoice("male", 0);
genderArgument.addChoice("female", 1);
// How old is the user? The user can give numbers from 0 to 99.
NumberArgument ageArgument = new NumberArgument("age", "Your age (number between 0 and 99)");
ageArgument.setRange(0, 99);
// Let's ask the user to mention his or her best friend,
MentionArgument bestFriendArgument = new MentionArgument("bestfriend", "Your best friend on Discord");
// to mention his or her favourite channel on this server
ChannelArgument favouriteChannelArgument = new ChannelArgument("favouritechannel", "Your favourite channel on this server");
// and to send us a funny picture's url, that is just an optional argument
URLArgument funnyPictureArgument = new URLArgument("funnypicture", "A funny picture's url");
funnyPictureArgument.setOptional();
// Add arguments to the command
introduceCommand.addArgument(nameArgument, genderArgument, ageArgument, bestFriendArgument, favouriteChannelArgument, funnyPictureArgument);
// Let's make an action listener where we can listen for inputs.
// user is the sender, args is an array of the converted arguments and responder is a class that manages responses
introduceCommand.setAction((sender, args, responder) -> {
   // ValueArgument return as a Matcher by default
   Matcher fullName = args[0].get();
   String firstName = fullName.group("first");
   String lastName = fullName.group("last");

   // The gender contains integer values based on the key
   int gender = args[1].get();

   // The age from a NumberArgument so it's a number by default
   // It has been validated so range checking is unnecessary
   int age = args[2].get();

   User bestFriend = args[3].get();
   Channel favouriteChannel = args[4].get();

   // URL arguments are also getting converted into the corresponding type
   // This is an optional argument, so we need to check that this have been specified
   URL funnyPictureUrl = args.length < 6 ? null : args[5].get();

   // Something that uses all the arguments
   MessageBuilder responseMessage = new MessageBuilder();
   responseMessage.append("Hi " + firstName + " " + lastName + "! You are very " + (gender == 0 ? "handsome" : "beautiful") + " :heart: As I can see you are " + (age >= 10 && age < 20 ? "" : "not ") + "a teenager.");
   if (bestFriend.isBot()) {
       responseMessage.append(" What? Did you know that your best friend is a BOT?");
   }
   if (favouriteChannel.getId() == 791658134153330718L) {
       responseMessage.append(" That's my favourite channel too!");
   }
   if (funnyPictureUrl != null) {
       responseMessage.append(" Here is your funny picture: " + funnyPictureUrl.toString());
   }

   // Send the response message to the user
   responder.respondNow()
           .setContent(responseMessage.getStringBuilder().toString())
           .respond();
});
// And don't forget to register the command on the server(s). (I always forget it and never know what's wrong :D)
CommandHandler.registerCommand(introduceCommand, myServer);
```  

![Example](https://imgur.com/swqZYXH.png)
  
> You can find a full code [here](https://github.com/S3nS3IW00/JCommands/blob/master/src/test/java/me/s3ns3iw00/jcommands/TestMain.java) what runs a bot with the example codes above.

## Wiki
You can find more information about how to use `JCommands` on the [Wiki](https://github.com/S3nS3IW00/JCommands/wiki) page.
  
## Dependencies  
As you can see in the description, this is a Javacord extension. Javacord is a framework and it so much easier to create bots with it.  
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
   CommandHandler.setApi(DiscordApi);  
   ```  
3. Create an error listener and as many commands as you want.  
4. Enjoy!