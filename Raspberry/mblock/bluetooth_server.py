# pybluez library
import bluetooth
from mock import bluetooth

server_socket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
client_sockets = []

server_socket.bind(("", bluetooth.PORT_ANY))
port = server_socket.getsockname()[1]
uuid = "00001101-0000-1000-8000-00805F9B34FB"

# advertise service
bluetooth.advertise_service(
    server_socket,
    "Validation Host",
    service_id=uuid,
    service_classes=[uuid, bluetooth.SERIAL_PORT_CLASS],
    profiles=[bluetooth.SERIAL_PORT_PROFILE],
)

server_socket.listen(1)

# accept incoming connections
client_sock, client_info = server_socket.accept()
# client_sockets.append(client_sock)
print "Accepted Connection from ", client_info

data = client_sock.recv(1024)
print "received [%s] \n " % data  
client_sock.close()
server_socket.close()  