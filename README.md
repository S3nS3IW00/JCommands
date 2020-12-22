# JCommands [![](https://img.shields.io/badge/Version-1.4.0-blue)](https://github.com/S3nS3IW00/JCommands) [![](https://img.shields.io/badge/Javadoc-Latest-green)](https://s3ns3iw00.github.io/JCommands/javadoc/)  
With this Javacord extension you can create commands within 1 minute that are only working in private or in the specified channels and categories with the specified roles and arguments.  There are so many useful prewritten argument types that can be used while creating a command.
  
## Code example  
The following examples might help you. Don't hesitate to use them!  
### Registering the error listener  
Error listener allows you to create custom functions for every error type.  
```java  
MessageCommandHandler.setOnError((type, cmd, sender, msg) -> {  
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
// Create the command what is will only be available in the server  
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
introduceCommand.addArguments(new Argument("male"), new Argument("female"));  
// And lastly the age. The user can give numbers from 0 to 99. The input will be converted into Integer.
introduceCommand.addArguments(new RegexArgument("your age", "([0-9]|[1-9][0-9])", Integer.class));  
// Let's make an action listener where we can listen for inputs. The raw array contains the inputs from the user, and the args array contains the converted ones.
introduceCommand.setAction((sender, raw, args, msg) -> {
  // The RegexArgument return as a Matcher by default
  Matcher fullName = args[0].get();  
  String firstName = fullName.group("first");  
  String lastName = fullName.group("last");  
  
  // The age is can be decleared as an integer without parsing because it has been converted
  int age = args[2].get();

  msg.getChannel().sendMessage("Hi " + firstName + " " + lastName + "! As I can see you are " + (age >= 10 && age < 20 ? "" : "not ") + "a teenager.");  
});  
// And don't forget to register the command on the server(s). (I always forget it and never know what's wrong :D)  
MessageCommandHandler.registerCommand(introduceCommand, myServer);
```  
**Usage of this command:** `/iam <Firstname,Lastname> male|female <your age>`  
**Command example:** `/iam Jack,Johnson male 18` or `/iam Isabel,Hale female 25`  
  
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