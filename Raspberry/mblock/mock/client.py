def wait_connection():
    raw_input("***MOCK:CLIENT*** Press [ANY_KEY] for connect...")

def send_data():
    msg = "***MOCK:CLIENT*** Enter message for send: "
    received_data = []
    received_data.insert(0, raw_input(msg))
    return received_data
