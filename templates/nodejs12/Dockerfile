ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM node:alpine

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

# Add binaries to the path
ENV PATH="/usr/local/bin:${PATH}"

# Copy and install dependencies before function code
# this avoids reinstalling unchanged dependencies on each code change
COPY package.json /package.json
COPY *.lock *-lock.json package.json /

RUN yarn import || echo "Lockfile already exists"

RUN RUN set -ex; yarn install --production --frozen-lockfile --cache-folder /tmp/.cache; rm -rf /tmp/.cache;

# Install the userland function code.
COPY . .

# Expose the gateways proxy port
EXPOSE 9001/tcp

WORKDIR /

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# Set membrane child process command and args
CMD ["node", "index.js"]