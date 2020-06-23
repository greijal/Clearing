run:build
	java -jar build/libs/clearing-0.0.1-SNAPSHOT.jar

build:clean
	./gradlew clean build

clean:
	./gradlew clean