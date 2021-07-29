from typing import Union
from nitric.faas import start, Trigger, Response


async def handler(request: Trigger) -> Union[Response, dict, str]:
    return "Hello Python 3.7"


if __name__ == "__main__":
    start(handler)
