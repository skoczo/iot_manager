import requests
import random
import time

host = 'http://localhost'
port = '8443'
urlTemp = '/temperature'

sensor1 = "28ff88b893160585"
sensor2 = "28ff6ef293160466"


def request(sensor_id):
    requests.post(host + ':' + port + urlTemp, None, {'value': random_temp(), 'sensorId': sensor_id, 'deviceId': 'ccc'})


def random_temp():
    return "%2.2f" % ((random.random() * 100) % 2 + 19)


if __name__ == '__main__':
    while True:
        request(sensor1)
        request(sensor2)
        time.sleep(60 * 5)
