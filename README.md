# Database.com SDK for Java

For more information on how to use the Java SDK, please read our documentation at http://forcedotcom.github.com/java-sdk/force-sdk-overview

## How to build

The Database.com SDK is built using [Maven](http://maven.apache.org/) version 2.2.1.  Make sure you have the correct version of Maven installed on your system before attempting to build.

Run the following maven command to build from source without running any tests

    mvn clean install -DskipTests

Run the following maven command to build from source and run only unit tests

    mvn clean install

In addition to unit tests, there are functional and integration tests located in the javasdk-test submodule.  Before running the functional and integration tests, you'll need to supply test org credentials in the properties file javasdk-test/qa-utils/src/main/resources/force-sdk-test.properties

Run the following maven commands to compile and run all tests (unit, functional and integration)

    mvn clean install -DskipTests -DincludeTestModules
    mvn clean verify -DincludeTestModules

You can run specific test suites by changing your working directory to a test submodule directory, such as javasdk-test/force-jpa-test/ , and then running the following maven command

    mvn verify

Keep in mind that you still must run <code>mvn clean install -DskipTests -DincludeTestModules</code> from the root directory of the project before running any tests in the javasdk-test submodules, otherwise tests may fail.