# COMPLETED: CRIO_TASK_MODULE_DEPLOYMENT: Create the Dockerfile so that you can deploy your Spring Boot
# application easily.
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /qeatsbackend/build/libs/qeatsbackend.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# TIP(MODULE_DEPLOYMENT): Useful commands.
# sudo docker build -t qeats-server .
# sudo docker run -m 800m -v /tmp/container:/tmp:rw -p 80:8081 qeats-server