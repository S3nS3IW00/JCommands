# JCommands [![](https://img.shields.io/badge/Version-1.2.0-blue)]() [![](https://img.shields.io/badge/Javadoc-Latest-green)](https://s3ns3iw00.github.io/JCommands/javadoc/)  
With this `Javacord` extension you can **create commands within one minute** that are only working in private or in the specified channels and categories with the specified roles and arguments.  There are so many useful prewritten argument types that can be used while creating a command.
  
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
introduceCommand.allowedCategory(api.getChannelCategoryById(123456789).get());  
// But we have two channels in that category where the command should not to work  
introduceCommand.notAllowed(api.getTextChannelById(123456789).get(), api.getTextChannelById(987654321).get());  
// This command is only for users who has this role  
introduceCommand.roles(false, api.getRoleById(123456789).get());  
// Let's create our command's first argument. This argument only accepts letters with a capital letter at the beginning.  
introduceCommand.arguments(new RegexArgument("Firstname,Lastname", "(?<first>[A-Z][a-z]+),(?<last>[A-Z][a-z]+)"));  
// We want to know the user's gender so make an argument that has two acceptable values. (One of them or both could be a regex argument or something else too)  
introduceCommand.arguments(new Argument("male"), new Argument("female"));  
// And lastly the age. The user can give numbers from 0 to 99.  
introduceCommand.arguments(new RegexArgument("your age", "([0-9]|[1-9][0-9])", Integer.class));  
// Let's make an action listener where we can listen for inputs. The difference between raw and args is that when you use RegexArgument you can get the Matcher from the argument so you can play with groups easily.  
introduceCommand.action((sender, raw, args, msg) -> {  
    Matcher fullName = args[0].get();  
    String firstName = fullName.group("first");  
    String lastName = fullName.group("last");  
  
    int age = args[2].get();  
 msg.getChannel().sendMessage("Hi " + firstName + " " + lastName + "! As I can see you are " + (age < 20 ? "" : "not " + "a teenager."));});  
// And don't forget to register the command. (I always forget it and never know what's wrong :D)  
MessageCommandHandler.registerCommand(introduceCommand);  
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
1. Download the [latest release](https://github.com/S3nS3IW00/JCommands/releases/latest) or clone repository  
```
git clone https://github.com/S3nS3IW00/JCommands.git  
```  
2. Initiate the listener  
```java  
MessageCommandHandler.setServer(server);  
```  
3. Create an error listener and as many commands as you want.  
4. Enjoy!