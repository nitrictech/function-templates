# Template: Java 11

[![javadoc](https://javadoc.io/badge2/io.nitric/java-sdk/javadoc.svg)](https://javadoc.io/doc/io.nitric/java-sdk)

## Usage

The Java 11 template creates a standalone Nitric Function application which integrates with the Nitric Membrane.

### Nitric Run

You can quickly run your function using the Nitric CLI `run` command.

```bash
$ nitric run
✔ Building Services
✔ Creating docker network
✔ Creating Volume: my-function-vol-80bb1e77
✔ Running Services
✔ Starting API Gateways
✔ Starting Entrypoints
 Service Port
 ─────── ─────
 example 49152
Running, press 'Q' to clean up and exit... ⡿
```

Once your function is running you can invoke it via a browser with the specified port (e.g. `http://localhost:49152/`) or by using `curl`:

```bash
$ curl localhost:49152
Nitric Java 11 Template - Success
$
```

### Java Project

The function project uses the Maven build system. The project includes a `NitricFunction` handler class with a `main()` entry point which initializes a `Faas` function server.

```Java
package com.example;

import io.nitric.faas.Faas;
import io.nitric.faas.Trigger;
import io.nitric.faas.NitricFunction;
import io.nitric.faas.Response;

public class Handler implements NitricFunction {

    @Override
    public Response handle(Trigger trigger) {
        return trigger.buildResponse("Nitric Java 11 Template - Success");
    }

    public static void main(String[] args) {
        Faas.start(new Handler());
    }

}
```

The Java function code and dependencies is packages into a FAT Jar file using Maven.

```shell
$ mvn clean package
```

You can run the Java application directly for debugging purposes via:

```shell
$ java -jar target\handler-1.0.jar

Faas listening on port 8080 with function: HelloWorld
```

## Docker

The Java application is then built into a Docker Image using the `Dockerfile` file.

```shell
docker build -f .nitric/templates/function/java11/Dockerfile \
    example/ -t function-handler
```

To run the Docker container use:

```
$ docker run -ip 9001:9001 function-handler
Registered Gateway Plugin
Starting Child Process
Services listening on: 127.0.0.1:50051
Waiting for active workers
Starting Worker Supervisor
Starting Gateway, 1 workers currently available
Received init request from worker
```

Please note the first time the docker build runs will take some time as all the Maven dependencies are downloaded. However after this builds will be much faster.

The default `Dockerfile` build will create an AdoptOpenJDK 11 Alpine image about 190 MB in size.

To maximise function performance please use the `Dockerfile.native` which will build a native static application using the GraalVM compiler. The native application Alpine image is about 90 MB in size.

```shell
docker build -f .nitric/templates/function/java11/Dockerfile.native \
    example/ -t function-handler-native
```

Note the GraalVM compiler will take much longer to compile (2-5 mins) but the resulting function will execute from a cold start much faster and consume much less memory.

```
$ docker run -ip 9001:9001 function-handler-native

Registered Gateway Plugin
Starting Function
Services listening on: 127.0.0.1:50051
Faas listening on port 8080 with function: HelloWorld
Starting Gateway
```