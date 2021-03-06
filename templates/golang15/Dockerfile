ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM golang:1.15-alpine3.13 as build

ARG HANDLER=main.go

RUN apk update
RUN apk upgrade

# RUN apk add --update go=1.8.3-r0 gcc=6.3.0-r4 g++=6.3.0-r4
RUN apk add --no-cache git gcc g++ make

WORKDIR /app/

# Copy dependency files
COPY go.mod *.sum /

# Install go dependencies
RUN go mod download

# Copy the function source
COPY . .

WORKDIR /

# Build the function binary
RUN CGO_ENABLED=0 GOOS=linux go build -o /bin/function /app/${HANDLER}

# Fresh image to remove go build packages
FROM alpine

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

# Add binaries to the path
ENV PATH="/usr/local/bin:${PATH}"

COPY --from=build /bin/function /function
RUN chmod +x-rw /function

# Expose the gateways proxy port
EXPOSE 9001/tcp

# WORKDIR required to AWS lambda deployments
WORKDIR /

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# Set membrane child process command and args
CMD ["/function"]