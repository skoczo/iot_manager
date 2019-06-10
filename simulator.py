import requests
import random
import time

host = 'http://localhost'
port = '8443'
urlTemp = '/iot/temperature'

sensor1 = "28ff88b893160585"
sensor2 = "28ff6ef293160466"

token = '5ccf1c4c-90d8-42e5-8338-ad4d40f677b2'
#token = '748d34a0-67d6-4232-aa00-0263a4112ef7'

hed = {'Authorization': 'Bearer ' + token}


def request(sensor_id):
    response = requests.post(host + ':' + port + urlTemp, None,
                             {'value': random_temp(), 'sensorId': sensor_id, 'deviceId': 'ccc'}, headers=hed)
    print(response)


def random_temp():
    return "%2.2f" % ((random.random() * 100) % 2 + 19)


if __name__ == '__main__':
    while True:
        print('sent temp')
        request(sensor1)
        request(sensor2)
        time.sleep(5)
