FROM java:8
VOLUME /tmp
ADD build/libs/thriftclient.jar app.jar

EXPOSE 8080
EXPOSE 8990

# setup startup command
CMD ["bash", "-c", "java -jar  \
 ${JAVA_OPTS} \
 -Djava.rmi.server.hostname=${JMX_REMOTE_HOST} \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.local.only=false \
 -Dcom.sun.management.jmxremote.port=8990 \
 -Dcom.sun.management.jmxremote.rmi.port=8990 \
 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote=true \
 -Dspring.cloud.consul.host=$SPRING_CLOUD_CONSUL_HOST \
  app.jar"]