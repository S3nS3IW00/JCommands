# JCommands [![Maven Central](https://img.shields.io/maven-central/v/io.github.s3ns3iw00/jcommands?label=Latest%20version)](https://github.com/S3nS3IW00/JCommands/releases/latest) [![](https://img.shields.io/badge/Javadoc-Latest-green)](https://s3ns3iw00.github.io/JCommands/javadoc) [![](https://img.shields.io/badge/Javacord-3.8.0-blue)](https://github.com/Javacord/Javacord)

With this Javacord extension you can create Slash Commands on your Discord server within 1 minute with built-in
- input validating,
```java  
StringArgument<Integer> stringArgument = new StringArgument<>("number", "A number", Integer.class);  
stringArgument.getArgumentValidator().when(RegexPredicate.notValidFor("\\d+")).thenRespond(event -> {  
    event.getResponder().respondNow()  
        .setContent("The input is not a number!")  
        .respond();  
});  
```  
- value converting,
```java  
stringArgument.convertResult(value -> Integer.parseInt(value));  
```  
- autocompleting and
```java  
StringArgument<String> searchArgument = new StringArgument<>("search", "Start typing", String.class);  
SearchAutocomplete searchAutocomplete = new SearchAutocomplete(SearchAutocomplete.SearchType.CONTAINS, Arrays.asList(  
    "just some words like hello world"  
    .split(" ")  
))  
.ignoreCase();  
searchArgument.addAutocomplete(searchAutocomplete);  
```  
- concatenating
```java  
command.addConcatenator(new StringConcatenator<>(" "), stringArgument, searchArgument);  
```  

There are so many useful pre-written argument types that can be used while creating a command.

### Example command
This command is created just with about 50 lines of code including the response and error handling. It has a validated
argument with regex, an argument that has two different values that the user can choose from, a number argument that has
a range validation, a normal user mention argument, a normal channel mention argument and an optional attachment argument
with size and extension validation.

![Example](https://imgur.com/swqZYXH.png)

> You can find the full code [here](https://github.com/S3nS3IW00/JCommands/blob/master/src/test/java/me/s3ns3iw00/jcommands/TestMain.java) that runs a bot with the example command above.

## Wiki

You can find all the information about how to use `JCommands` on the [Wiki](https://github.com/S3nS3IW00/JCommands/wiki)
page.

## Dependencies

This is a Javacord extension. Javacord is a Discord bot framework and it so much easier to create
bots with it.

- [Javacord](https://github.com/Javacord/Javacord)

## Usage
Replace {VERSION} with the one of the released versions (the latest recommended)

### Maven
```xml  
<dependency>  
    <groupId>io.github.s3ns3iw00</groupId>  
    <artifactId>jcommands</artifactId>  
    <version>{VERSION}</version>  
</dependency>  
```  

### Gradle
```groovy  
dependencies {  
    implementation 'io.github.s3ns3iw00.jcommands:{VERSION}'  
}  
```  

## Contact

If you found a bug, or you just want a new feature in the next version, don't hesitate to report it on
the [issues](https://github.com/S3nS3IW00/JCommands/issues) page or feel free to open a pull request. You can contact me through
discord: [🆂🅴🅽🆂🅴🆈#0054](https://discord.com/users/249674530077802496)
