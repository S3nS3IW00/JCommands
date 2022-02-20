# JCommands [![](https://img.shields.io/badge/Version-4.1.1-blue)](https://github.com/S3nS3IW00/JCommands) [![](https://img.shields.io/badge/Javadoc-Latest-green)](https://s3ns3iw00.github.io/JCommands/javadoc/) [![](https://img.shields.io/badge/Javacord-3.4.0-red)](https://github.com/Javacord/Javacord)

With this Javacord extension you can create Slash commands within 1 minute with built-in validating, converting,
concatenating and extended permission checking for channels and categories. There are so many useful pre-written
argument types that can be used while creating a command.

### Example command

This command is created just with about 50 lines of code including the response and error handling. It has a validated
argument with regex, an argument that has two different values that the user can choose from, a number argument that has
a range validation, a normal user mention argument, a normal channel mention argument and an optional URL argument that
is a text input with regex validation. In the response the first argument is separated with regex groups into two
different value, and the response based on every argument's value.

![Example](https://imgur.com/swqZYXH.png)

> You can find a full code [here](https://github.com/S3nS3IW00/JCommands/blob/master/src/test/java/me/s3ns3iw00/jcommands/TestMain.java) that runs a bot with the example command above.

## Wiki

You can find all the information about how to use `JCommands` on the [Wiki](https://github.com/S3nS3IW00/JCommands/wiki)
page.

## Dependencies

As you can see in the description, this is a Javacord extension. Javacord is a framework and it so much easier to create
bots with it.

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
3. Create an error listener and as many command as you want.
4. Enjoy!