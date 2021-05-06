
ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM node:alpine as build

# FIXME: Remove once the @nitric/sdk package is made public
# Token should be read-only and revoked once this package is public
ARG NPM_TOKEN
ENV YARN_CACHE_FOLDER=/dev/shm/yarn_cache

# Copy and install dependencies before function code
# this avoids reinstalling unchanged dependencies on each code change
COPY ./function/package.json /package.json
COPY ./function/*.lock Dockerfile /
COPY ./function/*-lock.json Dockerfile /

RUN echo "//registry.npmjs.org/:_authToken=$NPM_TOKEN" > .npmrc

RUN yarn import || echo "Lockfile already exists"

RUN yarn install --frozen-lockfile
# Copy sources
COPY /function .
# Build it
RUN yarn build

FROM node:alpine
RUN apk --no-cache add ca-certificates
ENV YARN_CACHE_FOLDER=/dev/shm/yarn_cache

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

COPY --from=build .npmrc .npmrc
COPY --from=build package.json package.json
COPY --from=build Dockerfile *.lock /
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