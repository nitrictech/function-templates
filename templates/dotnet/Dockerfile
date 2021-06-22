####
# Nitric Membrane infrastructure
ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane

#Build for release
FROM mcr.microsoft.com/dotnet/sdk:5.0 AS build

RUN mkdir -p /home/app/src/function
WORKDIR /home/app/src/function/
COPY ./function/src/*.csproj .
RUN dotnet restore

COPY . .
RUN dotnet build

RUN dotnet publish -c Release -o /publish --no-restore

FROM mcr.microsoft.com/dotnet/runtime:5.0

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

COPY --from=build /publish .

EXPOSE 9001/tcp

# AWS Lambda requires the working directory to be explicitly set
WORKDIR /

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# CMD required for AWS Lambda, this input is ignored by the membrane.
CMD ["dotnet", "HelloWorld.dll"]