FROM java:8
VOLUME /tmp
ADD build/libs/thriftclient.jar app.jar

EXPOSE 8080
EXPOSE 8990
EXPOSE 4002

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
 -Dbalance.timeout=$BALANCE_TIMEOUT \
 -Dspring.cloud.consul.host=$SPRING_CLOUD_CONSUL_HOST \
 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4002,suspend=n \
  app.jar"]