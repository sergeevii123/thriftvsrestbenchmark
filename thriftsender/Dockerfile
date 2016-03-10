FROM java:8
VOLUME /tmp
ADD build/libs/thriftsender.jar app.jar

EXPOSE 8080
EXPOSE 8991
EXPOSE 4003

# setup startup command
CMD ["bash", "-c", "java -jar  \
 ${JAVA_OPTS} \
 -Djava.rmi.server.hostname=${JMX_REMOTE_HOST} \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.local.only=false \
 -Dcom.sun.management.jmxremote.port=8991 \
 -Dcom.sun.management.jmxremote.rmi.port=8991 \
 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote=true \
 -Dinitial.delay=$INITIAL_DELAY \
 -Dis.initial.delay.random=$IS_INITIAL_DELAY_RANDOM \
 -Dspring.cloud.consul.host=$SPRING_CLOUD_CONSUL_HOST \
 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4003,suspend=n \
  app.jar"]