from proto.commands_pb2 import Request
from proto.commands_pb2 import Response
import commands
from google.protobuf.message import DecodeError


class Command():
    executor = None
    param = None
    request = Request()

    def __init__(self, msg):
        self.request.ParseFromString(msg)
        self.executor, self.param = get_command(self.request)

    def run(self):
        self.executor(self.param)
        return None


def process(recv_data):
    try:
        cmd = Command(recv_data)
        cmd.run()
    except DecodeError:
        return error_response()
    return None


def error_response():
    res = Response()
    res.status = Response.ERROR
    return res.SerializeToString()


def get_command(request):
    if request.type == Request.OPEN and request.open:
        return commands.open, request.open
    elif request.type == Request.CLOSE and request.close:
        return commands.close, request.close
    else:
        raise ValueError