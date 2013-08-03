from mock import client
import logging
log = logging.getLogger("[MOCK]BLUETOOTH")

RFCOMM = 3
PORT_ANY = 0
SERIAL_PORT_CLASS = "1101"
SERIAL_PORT_PROFILE = (SERIAL_PORT_CLASS, 0x0100)


class BluetoothSocket:
    def __init__(self, proto):
        if not proto in [RFCOMM]:
            raise ValueError("invalid protocol")

        self.proto = proto
        self.received_data = []

        self.bound = False
        self.listening = False
        self.connected = False

        self.bind = self.rfcomm_bind
        self.getsockname = self.rfcomm_getsockname
        self.listen = self.rfcomm_listen
        self.accept = self.rfcomm_accept
        self.recv = self.rfcomm_recv
        self.close = self.rfcomm_close
        self.send = self.rfcomm_send
        # self.connect         = self.rfcomm_connect
        # self.setblocking     = self.rfcomm_setblocking
        # self.settimeout      = self.rfcomm_settimeout
        # self.gettimeout      = self.rfcomm_gettimeout
        # self.dup             = self.rfcomm_dup
        # self.makefile        = self.rfcomm_makefile
        # self.fileno          = self.rfcomm_fileno
        # self.__make_cobjects = self.__rfcomm_make_cobjects
        # self._advertise_service = self.__rfcomm_advertise_service


    def rfcomm_bind(self, addrport):
        addr, port = addrport

        if len (addr):
            raise ValueError("Widcomm stack can't bind to user-specified adapter")

        self.bound = True
        log.debug("Bound to port %s", port)

    def rfcomm_getsockname(self):
        if not self.bound:
            raise BluetoothError("Socket not bound")
        addr = 0
        port = 0
        return addr, port

    def rfcomm_listen(self, backlog):
        self.listening = True
        log.debug("Start listening")

    def rfcomm_accept(self):
        # if self.connected:
        #     raise BluetoothError("already connected")

        while self.listening and not self.connected:
            log.debug("Waiting for connection")

            client.wait_connection()
            self.connected = True

        if self.connected:
            client_bdaddr = 0
            client_port = 0

            clientsock = BluetoothSocket(RFCOMM)

            return clientsock, (client_bdaddr, client_port)

    def rfcomm_recv(self, numbytes):

        while not self.received_data:
            self.received_data = client.send_data()

        if self.received_data:
            data = self.received_data.pop(0)
            if len(data) > numbytes:
                self.received_data.insert(0, data[numbytes:])
                return data[:numbytes]
            else:
                return data

    def rfcomm_send(self, data):
        client.recv_data(data)

    def rfcomm_close(self):
        self.bound = False
        self.listening = False
        self.connected = False
        print "***MOCK:BLUETOOTH*** Bluetooth socket close"


def advertise_service( sock, name, service_id = "", service_classes = [], \
                       profiles = [], provider = "", description = "", protocols = []):
    log.debug("Advertise service started")

class BluetoothError (IOError):
    pass