ARG PROVIDER=local
FROM nitricimages/membrane-${PROVIDER}:latest as membrane
FROM python:3.7-slim

# Add the membrane server binary and plugins
COPY --from=membrane /membrane /usr/local/bin/membrane
RUN chmod +x-rw /usr/local/bin/membrane

# Add binaries to the path
ENV PATH="/usr/local/bin:${PATH}"

# Copy and install dependencies before function code
# this avoids reinstalling unchanged dependencies on each code change
COPY requirements.txt requirements.txt
WORKDIR /

RUN pip install --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# Install the userland function code.
COPY . .

# Expose the gateways proxy port
EXPOSE 9001/tcp

# Run the Nitric Membrane
# This process will load membrane provider plugins and kick-off the userland process
# full path Entrypoint required for AWS Lambda
ENTRYPOINT ["/usr/local/bin/membrane"]
# CMD required for AWS Lambda, this input is ignored by the membrane.
CMD ["python", "index.py"]