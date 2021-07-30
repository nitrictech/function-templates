####
# Nitric Membrane infrastructure
ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane

#Build for release
FROM mcr.microsoft.com/dotnet/sdk:5.0 AS build

RUN mkdir -p /home/app/
WORKDIR /home/app/
COPY *.csproj *.sln ./
RUN dotnet restore

COPY . .
RUN dotnet build

RUN dotnet publish -p:PublishSingleFile=true --self-contained true -r linux-musl-x64 -c Release -o /publish

FROM mcr.microsoft.com/dotnet/core/runtime-deps:3.1.0-alpine3.10

WORKDIR /

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

# Copy all files from publish directory to the root.
RUN mkdir Function
COPY --from=build /publish ./Function
RUN chmod +x -R ./Function


EXPOSE 9001/tcp

#ENV MEMBRANE_MODE="HTTP_FAAS"

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# CMD required for AWS Lambda, this input is ignored by the membrane.
CMD ["./Function/Function"]

