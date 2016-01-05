from multiprocessing import Process, Value, Array
import serial
from flask import Flask
import time
app = Flask(__name__)


def manipulate_data(count):
    serial1 = serial.Serial('/dev/ttyACM0', 19200)
    serial2 = serial.Serial('/dev/ttyACM1', 9600)
    while True:
        ser1 = serial1.read(1)
        ser2 = serial2.read(1)
        print ser1, ser2
        valid = True

        if (ser1=='1' or ser2=='1'):
            if (ser1=='1'):
                print 'Ser1 blocked. About to check if the person is going in.'
                t = time.time()
                while ser2=='0':
                    serial2.flushInput()
                    ser2 = serial2.read(1)
                    if (time.time() - t) > 5:
                        valid = False
                        print "time out. ser1 unblock."
                        time.sleep(1)
                        break
                while ser2=='1':
                    print 'Ser2 blocked as well.'
                    serial2.flushInput()
                    ser2 = serial2.read(1)
                    if (time.time() - t) > 5:
                        valid = False
                        print "time out."
                        time.sleep(1)
                        break
                if valid:
                    count.value += 1
                    print "Current people count changed: " + str(count.value)

            else: #serial 2 is blocked.
                print 'Ser2 blocked. About to check if the person is going out.'
                t = time.time()
                while ser1=='0':
                    serial1.flushInput()
                    ser1 = serial1.read(1)
                    if (time.time() - t) > 5:
                        valid = False
                        print "time out. ser2 unblock."
                        time.sleep(1)
                        break
                while ser1=='1':
                    print 'Ser1 blocked as well.'
                    serial1.flushInput()
                    ser1 = serial1.read(1)
                    if (time.time() - t) > 5:
                        valid = False
                        print "time out."
                        time.sleep(1)
                        break
                if valid:
                    count.value -= 1
                    print "Current people count changed: " + str(count.value)
        serial1.flushInput()
        serial2.flushInput()

@app.route('/')
def hello_world():
    return str(count.value)

if __name__ == '__main__':
    count = Value('i', 0)
    p = Process(target=manipulate_data, args=(count,))
    p.start()
    app.run(host="0.0.0.0", port=80, debug=True)
    p.join()
