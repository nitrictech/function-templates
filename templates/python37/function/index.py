from nitric.faas import start, Request, Response


def handler(request: Request) -> Response:
    return Response("Python 3.7 Success!")


if __name__ == "__main__":
    start(handler)
