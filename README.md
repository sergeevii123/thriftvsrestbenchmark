##First version
###How to run locally:
* gradle clean build dB
* change in docker-compose.yml \<HOST_IP\> for ip of the host where you will benchmark
* docker-compose up -d
* Open Consul and wait for all service to turn green
* start aggregator manually 
 
when aggregator finishes connect with jconsole/jvisualvm to \<HOST_IP>\:8989 - rest and \<HOST_IP>\:8990 - thrift 

mbeans registry: benchmark.rest || benchmark.thrift 

In current version aggregator just starts test for thrift and rest.

For configure number of threads and length of sending file look in benchmark.Aggregator class
