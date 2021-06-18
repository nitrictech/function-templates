from nitric.faas import start, Trigger, Response


def handler(request: Trigger):
    return "Hello Python 3.7"


if __name__ == "__main__":
    start(handler)
