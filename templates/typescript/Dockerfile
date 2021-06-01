ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM node:alpine as build

ENV YARN_CACHE_FOLDER=/dev/shm/yarn_cache

# Copy and install dependencies before function code
# this avoids reinstalling unchanged dependencies on each code change
COPY package.json *.lock *-lock.json /

RUN yarn import || echo "Lockfile already exists"

RUN yarn install --frozen-lockfile
# Copy sources
COPY . .
# Build it
RUN yarn build

# Final Image
FROM node:alpine
RUN apk --no-cache add ca-certificates
ENV YARN_CACHE_FOLDER=/dev/shm/yarn_cache

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

COPY --from=build .npmrc package.json *.lock /
RUN yarn install --production --frozen-lockfile

COPY --from=build /lib/ .

# Add binaries to the path
ENV PATH="/usr/local/bin:${PATH}"

# Expose the gateways proxy port
EXPOSE 9001/tcp

# AWS Lambda requires the working directory to be explicitly set
WORKDIR /

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# Set membrane child process command and args
CMD ["node", "index.js"]