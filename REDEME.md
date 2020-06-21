# Site Clearing Simulation

Shelll system to simulate land clearing

Veersion : 0.0.1-SNAPSHOT

- Java 1.8
- Spring Shell
- Mockito
- Junit
- Gradle


### Build

```
./gradlew clean build   
```
### Running

```
java -jar build/libs/clearing-0.0.1-SNAPSHOT.jar 
```

### Commands
```
AVAILABLE COMMANDS
  
  Built-In Commands
          clear: Clear the shell screen.
          exit, quit: Exit the shell.
          help: Display help about available commands.
          history: Display or save the history of previously run commands
          script: Read and execute commands from a file.
          stacktrace: Display the full stacktrace of the last error.
  
  bulldozer
          a, advance: Move Bulldozer
          l, left: Turn bulldozer left 
          r, right: Turn bulldozer right 
  
  site
          load: Load new site map file
```