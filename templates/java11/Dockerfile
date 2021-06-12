####
# Nitric Membrane infrastructure
ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane

####
# Build JAR
FROM maven:3-openjdk-11 as maven

# download maven plugins
COPY ./pom.xml pom.xml
RUN mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

# build function JAR file
COPY ./src ./src
RUN mvn clean package

####
# Build production image
FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.10_9

ARG TARGET_JAR=target/hello-world-1.0.jar

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

COPY --from=maven ${TARGET_JAR} function.jar

# TODO: need CLI update to stage the parent directory nitric.yaml file
# COPY .nitric/nitric.yaml .nitric/nitric.yaml

# Expose the gateways proxy port
EXPOSE 9001/tcp

# AWS Lambda requires the working directory to be explicitly set
WORKDIR /

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# Set membrane child process command and args
CMD ["java", "-jar", "function.jar"]