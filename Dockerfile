FROM maven:3.6.0 as BUILD
COPY . /usr/src/app
RUN mvn -e -X -DskipTests --batch-mode -f /usr/src/app/pom.xml clean package
FROM openjdk:11
ENV PORT 8080

EXPOSE 8080

COPY --from=BUILD /usr/src/app/target /opt/target

WORKDIR /opt/target

CMD ["/bin/sh", "-c", "find -type f -name '*.jar' | xargs java -jar"]