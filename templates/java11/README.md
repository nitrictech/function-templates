# Template: Java 11

## Usage

The Java 11 template creates a standalone Nitric Function application which integrates with the Nitric Membrane.

The Java function class provides a `main()` entry point which initializes a `Faas` function server.

```Java
package com.example;

import io.nitric.faas.Faas;
import io.nitric.faas.NitricEvent;
import io.nitric.faas.NitricFunction;
import io.nitric.faas.NitricResponse;

public class HelloWorld implements NitricFunction {

    @Override
    public NitricResponse handle(NitricEvent event) {
        return NitricResponse.build("Hello World");
    }

    public static void main(String[] args) {
        new Faas().start(new HelloWorld());
    }
}
```

The Java function code and dependencies is packages into a FAT Jar file using Maven.

```shell
$ mvn clean package
```

You can run the Java application directly for debugging purposes via:

```shell
$ java -jar target\hello-world-1.0.jar

Faas listening on port 8080 with function: HelloWorld
```

## Docker

The Java application is then built into a Docker Image using the `Dockerfile` file.

```shell
docker build . -t hello-world
```

To run the Docker container use:

```
$ docker run -ip 9001:9001 hello-world

Registered Gateway Plugin
Starting Function
Services listening on: 127.0.0.1:50051
Starting Gateway
Faas listening on port 8080 with function: HelloWorld
```

Please note the first time the docker build runs will take some time as all the Maven dependencies are downloaded. However after this builds will be much faster.

The default `Dockerfile` build will create an AdoptOpenJDK 11 Alpine image about 190 MB in size.

To maximise function performance please use the `Dockerfile.native` which will build a native static application using the GraalVM compiler. The native application Alpine image is about 90 MB in size.

```shell
$ docker build . -f Dockerfile.native -t hello-world-native
```

Note the GraalVM compiler will take longer to compile but the resulting function will execute from a cold start much faster and consume much less memory.

```
$ docker run -ip 9001:9001 hello-world-native

Registered Gateway Plugin
Starting Function
Services listening on: 127.0.0.1:50051
Faas listening on port 8080 with function: HelloWorld
Starting Gateway
```