ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM node:alpine

# FIXME: Remove once the @nitric/sdk package is made public
# Token should be read-only and revoked once this package is public
ARG NPM_TOKEN

# Copy and install dependencies before function code
# this avoids reinstalling unchanged dependencies on each code change
RUN echo "//registry.npmjs.org/:_authToken=$NPM_TOKEN" > .npmrc

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

# Add binaries to the path
ENV PATH="/usr/local/bin:${PATH}"

# Copy and install dependencies before function code
# this avoids reinstalling unchanged dependencies on each code change

COPY ./function/package.json /package.json
COPY ./function/*.lock Dockerfile /
COPY ./function/*-lock.json Dockerfile /

RUN yarn import || echo "Lockfile already exists"

RUN yarn install --production --frozen-lockfile

# Install the userland function code.
COPY ./function/ .

# Expose the gateways proxy port
EXPOSE 9001/tcp

WORKDIR /

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# Set membrane child process command and args
CMD ["node", "index.js"]