import bluetooth
import threading
import cmd_handler
from config import server_uuid
from config import server_name

import logging
log = logging.getLogger("BLUETOOTH_SERVER")
#TEST
from config import TEST
if TEST:
    import time
    log.debug("Use mock bluetooth module")
    from mock import bluetooth

# run bluetooth server
server_socket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
server_socket.bind(("", bluetooth.PORT_ANY))
server_socket.listen(1)
server_port = server_socket.getsockname()[1]

# run advertise service
bluetooth.advertise_service(
    server_socket,
    server_name,
    service_id=server_uuid,
    service_classes=[server_uuid, bluetooth.SERIAL_PORT_CLASS],
    profiles=[bluetooth.SERIAL_PORT_PROFILE],
)


class clientThread(threading.Thread):
    def __init__(self, client_sock, client_info):
        threading.Thread.__init__(self)
        self.sock = client_sock
        self.info = client_info

    def run(self):
        try:
            while True:
                recv_data = self.sock.recv(1024)
                log.debug("From %s received [%s]" % (self.info, recv_data))
                if len(recv_data) == 0:
                    break
                send_data = cmd_handler.process(recv_data)
                log.debug("To %s sent [%s]" % (self.info, send_data))
                self.sock.send(send_data)
        except IOError:
            pass
        self.sock.close()
        print self.info, ": disconnected"

while True:
    client_sock, client_info = server_socket.accept()
    print client_info, ": connection accepted"
    echo = clientThread(client_sock, client_info)
    echo.setDaemon(True)
    echo.start()
    if TEST:
        time.sleep(360)

# # accept incoming connections
# client_sock, client_info = server_socket.accept()
# # client_sockets.append(client_sock)
# print "Accepted Connection from ", client_info
#
# data = client_sock.recv(1024)
# print "received [%s] \n " % data
# client_sock.close()
# server_socket.close()