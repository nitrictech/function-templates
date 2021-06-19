ARG PROVIDER=local
FROM composer:2.0 AS composer
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM php:8.0-alpine as build

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

# Add binaries to the path
ENV PATH="/usr/local/bin:${PATH}"

# copy the Composer PHAR from the Composer image into the PHP image
COPY --from=composer /usr/bin/composer /usr/bin/composer

RUN apk add --no-cache git

# create non-root user
RUN addgroup -S nitric && adduser -S -g nitric nitric && \
    mkdir -p /home/nitric

WORKDIR /home/nitric

COPY ./composer.json *.lock ./

# Composer requirements
# TODO: Do we need this?
# ARG COMPOSER_AUTH='{}'
# ENV COMPOSER_AUTH=${COMPOSER_AUTH}

RUN chown -R nitric /home/nitric

USER nitric

# Install Composer Dependecies
RUN [[ -f composer.lock || -f composer.json ]] && composer install --no-dev

# Install the userland function code.
COPY . .

# Expose the gateways proxy port
EXPOSE 9001/tcp

ENV MEMBRANE_MODE="HTTP_FAAS"

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]

# Set membrane child process command and args
CMD ["php", "index.php"]