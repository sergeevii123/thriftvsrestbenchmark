###How to run locally on Linux and docker0 ip is 172.17.0.1:
* ./gradlew clean build dB
* ./gradlew startDockers
* Open Consul 127.0.0.1:8500 in browser and wait for all services('thriftclient', 'thriftsender', 'restclient', 'restsender') to turn green
* ./gradlew test:bootRun

###How to run locally on docker machine

Ensure your docker machine has at least 4 cpu, 8192 ram and max file length 256*1024 -Dfile.length=262144. Or your load test will not be representative

* ./gradlew clean build dB
* ./gradlew -Dhost.for.test=\<Your docker machine IP> startDockers
* Open Consul \<Your docker machine IP>:8500 in browser and wait for all services to turn green
* ./gradlew -Dfile.length=262144 -Dconsul.for.test=\<Your docker machine IP> test:bootRun

###When test finishes

connect with jconsole/jvisualvm to \<Your host IP>:8989 - rest and \<Your host IP>:8990 - thrift

mbeans registry: benchmark.rest || benchmark.thrift

test name pattern: test-\<test number>-tc-\<thread count>-d-\<duration>-fl-\<file length>

For configure number of threads, length of sending file(bytes) and duration(seconds) specify:

 -Dthread.count(default: 2)

 -Dfile.length(default: 2\*1024\*1024)

 -Dduration(default: 30)

example: ./gradlew -Dthread.count=4 -Dfile.length=1048576 -Dduration=10 test:bootRun

Rest uses application/octet-stream

2 modes:
* mode1 - client sends get request for file and current time on sender. Timer updates on client when response from sender is obtained.
Timer update = current time on client - time from sender response.
* mode2 - sender sends put request with file and current time to client. Timer updates on client when put request is finished.
Timer update = current time on client - time from sender put request.

After timer update client checks that file length equals specified file length.

If they are different client writes error message to client log which is in path ./logs relative to docker-compose.yml (for test on docker machine connect directly to containers)

commands to run mode2:

./gradlew -Dhost.for.test=\<Your host IP> -Dtest.mode=mode2 startDockers

./gradlew -Dtest.mode=mode2 test:bootRun


###Remote deploy senders:

* push senders to your local docker registry,
* specify consulIp, discoveryIpAddress, jmxRemoteHost in common.yaml
* specify \<TEST_ENV> in hosts
* specify \<REMOTE_USER> in ansible.cfg
* delete restsender and thriftsender in docker-compose.yaml
* docker-compose up -d
* run ansible script from deployment/ansible with command: ansible-playbook playbooks/deploySenders.yaml
