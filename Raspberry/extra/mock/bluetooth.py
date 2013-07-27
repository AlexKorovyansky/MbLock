RFCOMM = 3
PORT_ANY = 0
SERIAL_PORT_CLASS = "1101"
SERIAL_PORT_PROFILE = (SERIAL_PORT_CLASS, 0x0100)


class BluetoothSocket:
    def __init__(self, proto):
        if not proto in [RFCOMM]:
            raise ValueError("invalid protocol")

        self.proto = proto
        self.bound = False
        self.listening = False

        self.bind = self.rfcomm_bind
        self.getsockname = self.rfcomm_getsockname
        self.listen = self.rfcomm_listen
        self.accept = self.rfcomm_accept
        self.connected = False
        # self.connect         = self.rfcomm_connect
        # self.send            = self.rfcomm_send
        # self.recv            = self.rfcomm_recv
        # self.close           = self.rfcomm_close
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
        print "***MOCK:BLUETOOTH*** Bound to port ", port

    def rfcomm_getsockname(self):
        if not self.bound:
            raise BluetoothError("Socket not bound")
        addr = 0
        port = 0
        return addr, port

    def rfcomm_listen(self, backlog):
        self.listening = True
        print "***MOCK:BLUETOOTH*** Start listening"

    def rfcomm_accept(self):
        if self.connected:
            raise BluetoothError("already connected")

        while self.listening and not self.connected:
            print ("***MOCK:BLUETOOTH*** Waiting for connection")
            raw_input("***MOCK:BLUETOOTH*** |Phone| Press [ANY_KEY] for connect...")
            self.connected = True

        if self.connected:
            client_bdaddr = 0
            client_port = 0

            clientsock = BluetoothSocket(RFCOMM)

            # now create new C++ objects
            self.__rfcomm_make_cobjects()

            return clientsock, (client_bdaddr, client_port)



def advertise_service( sock, name, service_id = "", service_classes = [], \
                       profiles = [], provider = "", description = "", protocols = []):
    print "***MOCK:BLUETOOTH*** Advertise service started "

class BluetoothError (IOError):
    pass