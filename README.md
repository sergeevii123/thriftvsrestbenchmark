##First version
###How to run locally:
* ./gradlew clean build dB
* ./gradlew -Dhost.for.test=\<Your host IP> -Dtest.mode=mode1 startDockers
* Open Consul \<Your host IP>:8500 in browser and wait for all service to turn green
* ./gradlew -Dtest.mode=mode1 aggregator:bootRun

when aggregator finishes connect with jconsole/jvisualvm to 127.0.0.1:8989 - rest and 127.0.0.1:8990 - thrift

mbeans registry: benchmark.rest || benchmark.thrift 

In current version aggregator just starts test for thrift and rest.

For configure number of threads and length of sending file look in benchmark.aggregatormode1.DemoMode1 for mode1 and benchmark.aggregatormode2.DemoMode2

Rest uses application/octet-stream

2 modes:
* mode1 - client sends get request for file (two way communication). Timer updates on client when response from sender is obtained.
* mode2 - sender sends put request with file to client (one way communication). Timer updates on client when put request is finished.

commands to run mode2:

./gradlew -Dhost.for.test=\<Your host IP> -Dtest.mode=mode2 startDockers

./gradlew -Dtest.mode=mode2 aggregator:bootRun


###Remote deploy senders:

* push senders to your local docker registry,
* specify consulIp, discoveryIpAddress, jmxRemoteHost in common.yaml
* specify \<TEST_ENV> in hosts
* specify \<REMOTE_USER> in ansible.cfg
* delete restsender and thriftsender in docker-compose.yaml
* docker-compose up -d
* run ansible script from deployment/ansible with command: ansible-playbook playbooks/deploySenders.yaml
