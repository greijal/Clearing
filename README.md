# Site Clearing Simulation

Shell system to simulate land clearing


![Alt text](img.png?raw=true "Title")


Version : 0.0.1-SNAPSHOT

- Java 1.8
- Spring Shell
- Mockito
- Junit
- Gradle



### Build

```
./gradlew clean build   
```
or 
```
make build
```

### Run Unit Test

```
 ./gradlew test
```

### Run Integration Test

```
./gradlew integrationTest  
```

### Running Script
```
$ java -jar clearing-0.0.1-SNAPSHOT.jar
clearing >> script --file script.txt
```

### Running

```
$ make run
```
or 
```
$ java -jar clearing-0.0.1-SNAPSHOT.jar
```


### Commands
```
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Bulldozer Commands
        a, advance: Move Bulldozer
        l, left: Turn bulldozer left 
        r, right: Turn bulldozer right 

Quit Commands
        exit, q, quit: Exit the shell.

Report Commands
        report: Print report

Site Commands
        load: Load new site map file

```