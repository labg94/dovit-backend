FROM openjdk:8-jre
WORKDIR /usr/share/dovit
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

#Dockerfile maven-openjdk-alpine

#FROM maven:3.6.0-jdk-8-alpine as BUILD
#COPY . /usr/src/app
#RUN mvn -e -X -DskipTests --batch-mode -f /usr/src/app/pom.xml clean package
#FROM openjdk:8u181-jdk-alpine3.8
#ENV PORT 8080
#
#EXPOSE 8080
#
#COPY --from=BUILD /usr/src/app/target /opt/target
#
#WORKDIR /opt/target
#
#CMD ["/bin/sh", "-c", "find -type f -name '*.jar' | xargs java -jar"]