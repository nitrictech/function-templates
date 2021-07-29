# Template: Python 3.7

## Usage

The main entrypoint to the Python 3.7 template is `index.py` in the `./template` subdirectory, which will be executed when the function starts. The index file should not be renamed or removed, however, additional files and subdirectories/packages are permitted. The index file should define a handler function and start processing faas requests via that function.

The default function in `index.py` look like this:

```python
from typing import Union
# Import form the Nitric Function-as-a-Service (faas) module
from nitric.faas import start, Trigger, Response

# Define a function that accepts faas triggers and returns responses (multiple return types are permitted)
async def handler(request: Trigger) -> Union[Response, dict, str]:
    return "Hello Python 3.7"

# Start handling triggers (e.g. HTTP Requests or Events) via the handler function
#   Note: 'start' will initialize the asyncio event loop for async handlers.
if __name__ == "__main__":
    start(handler)
```
