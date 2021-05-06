# Template: Python 3.7

## Usage

The main entrypoint to the Python 3.7 template is `index.py` in the `./function` subdirectory, which will be executed when the function starts. The index file should not be renamed or removed, however, additional files and subdirectories/packages are permitted. The index file should define a handler function and starts processing faas requests via that function.

The default function `index.py` look like this:

```python
# Import form the Nitric Function-as-a-Service (faas) module
from nitric.faas import start, Request, Response

# Define a method that accepts faas requests and returns faas Responses (or None)
def handler(request: Request) -> Response:
    return Response("Python 3.7 Success!")

# Start handling requests (triggers) via the handler function
if __name__ == "__main__":
    start(handler)
```