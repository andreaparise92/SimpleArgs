# Simple-Args for Java

Simple-Args is a simple java library to auto-generate the command-line, unix-like, prints of:
  - Application info;
  - Application usage;

## Table of Contents

1. [Description](#description)
1. [Example](#example)
1. [Library](#library)

## Description

Using @Arg annotation on a parameter, the program at startup will search that parameter within the program's arguments.
If present, it will be set and made available.
If the parameter is marked as required and does not come within the arguments,
the program will terminate with an error and will print in output its usage.

@Arg annotation:

```java

@arg(
    key = "key",
    description = "description",
    required = true|false         //default false
)

```

The @Application annotation allows to define the application's information that will be printed in output
such as: version, name, author, etc...

@Application annotation:

```java

@Application(
        name = "applicationName",  //default your class name
        version = "version",
        description = "description",
        author = "author",
        copyright = "copyright"
)

```

ArgsUtility interface provides the static methods to:
 - Print the application info of a class signed with annotation @Application;
 - Print the usage of the application reading its parameters signed with @Arg annotation;
 - Bind the input arguments with the class parameters;

ArgsUtility Interface:

```java

public interface ArgsUtility {

    static void parseArgs(Class cl,String[] args){...} // Parse and validate arguments

    static void printUsage(Class cl){...} // Print on console application usage

    static void printInfo(Class cl){...} // Print on console application info
)

```

## Example

This is a sample application:

  ```java

  import com.java.eju.arg.annotation.Application;
  import com.java.eju.arg.annotation.Arg;
  import static com.java.eju.arg.annotation.Argumented.parseArgs;

  @Application(
          version = "0.1",
          description = "Sample application for test.",
          author = "Andrea Parise",
          copyright = "Copyright © 2016 Andrea Parise"
  )
  public class MySampleApplication{

      @Arg(key = "u",description = "Your username", required = true)
      private static String username;

      @Arg(key = "p",description = "Your password", required = true)
      private static String password;

      @Arg(key = "n",description = "Your name")
      private static String name = "No name";

      @Arg(key = "s",description = "False to run silent")
      private static Boolean print = true;

      public static void print() {
          String out =  "MySimpleApplication{" +
                  "username='" + username + '\'' +
                  ", password='" + password + '\'' +
                  ", name='" + name + '\'' +
                  ", print=" + print +
                  '}';
          System.out.println(out);
      }

      public static void main (String [] args){

          parseArgs(MySampleApplication.class, args);

          if(print) print();

      }

  }

  ```

When user runs the sample application with wrong arguments, the output will be:

```code

MySampleApplication
Version 0.1
By Andrea Parise
Copyright © 2016 Andrea Parise

Description: Sample application for test.

Usage: java -jar MySampleApplication.jar -u <value> -p <value> [-options]
options:
-u or --username <value>   Your username
-p or --password <value>   Your password
-n or --name <value>       Your name
-s or --print              False to run silent

```

When user runs application sample with right arguments (e.g. -u root -p pwd), the output will be:

```code

Your bind parameters:{username='root', password='pwd', name='No name', print=true}

```

## Library

Import the jar provided in /lib directory to start using it in your project.