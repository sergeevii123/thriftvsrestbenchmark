##First version
###How to run locally:
* gradle clean build dB
* change in docker-compose.yml ip address
* docker-compose up -d
* Open Consul and wait for all service to turn green
* start aggregator manually (--spring.profiles.active=mode2 or mode1)
 
when aggregator finishes connect with jconsole/jvisualvm to 127.0.0.1:8989 - rest and 127.0.0.1:8990 - thrift

mbeans registry: benchmark.rest || benchmark.thrift 

In current version aggregator just starts test for thrift and rest.

For configure number of threads and length of sending file look in benchmark.aggregatormode1.DemoMode1 for mode1 and benchmark.aggregatormode2.DemoMode2

Rest uses application/octet-stream

2 modes:
* mode1 - client sends get request for file (two way communication)
* mode2 - sender sends put with file to client (one way communication)

###Remote deploy senders:

* push senders to your local docker registry,
* specify consulIp, discoveryIpAddress, jmxRemoteHost in common.yaml
* specify \<TEST_ENV> in hosts
* specify \<REMOTE_USER> in ansible.cfg
* delete restsender and thriftsender in docker-compose.yaml
* docker-compose up -d
* run ansible script from deployment/ansible with command: ansible-playbook playbooks/deploySenders.yaml
