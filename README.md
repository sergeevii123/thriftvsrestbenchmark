##First version
###How to run locally:
* gradle clean build dB
* change in docker-compose.yml \<HOST_IP\> for ip of the host where you will benchmark
* docker-compose up -d
* Open Consul and wait for all service to turn green
* configure balance timeout(look below) or leave default value 200 ms
* start aggregator manually
 
when aggregator finishes connect with jconsole/jvisualvm to \<HOST_IP>\:8989 - rest and \<HOST_IP>\:8990 - thrift 

mbeans registry: benchmark.rest || benchmark.thrift 

In current version aggregator just starts test for thrift and rest.

For configure number of threads and length of sending file look in benchmark.Aggregator class

use ansible scripts for benchmark over your network

###Balance Timeout

for balance frequency of client requests and responses from handler

balanseTimeout is sleep timeout after successful client get request and timer update, default value = 200 ms

monitor your network if your client sending more than receiving then you need to increase balance timeout

to configure in runtime: in mbean registry benchmark.rest or benchmark.thrift, open Settings -> Attributes -> BalanceTimeout (instruction for jconsole)