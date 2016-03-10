##First version
###How to run locally:
* gradle clean build dB
* change in docker-compose.yml ip address
* docker-compose up -d
* Open Consul and wait for all service to turn green
* start aggregator manually (--spring.profiles.active=mode2 or mode1)
 
when aggregator finishes connect with jconsole/jvisualvm to \<HOST_IP>\:8989 - rest and \<HOST_IP>\:8990 - thrift 

mbeans registry: benchmark.rest || benchmark.thrift 

In current version aggregator just starts test for thrift and rest.

For configure number of threads and length of sending file look in benchmark.aggregatormode1.DemoMode1 for mode1 and benchmark.aggregatormode2.DemoMode2

Rest uses application/octet-stream

2 modes:
* mode1 - client sends get request for file (two way communication)
* mode2 - sender sends put with file to client (one way communication)
