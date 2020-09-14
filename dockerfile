# FROM maven:3.5-jdk-8 as maven

# COPY ./pom.xml ./pom.xml

# RUN mvn dependency:go-offline -B

# COPY ./src ./src

# RUN mvn package

# FROM openjdk:8u171-jre-alpine

# # set deployment directory
# WORKDIR /enduscan

# # copy over the built artifact from the maven image
# COPY --from=maven target/enduscan-*.jar ./app.jar

# # set the startup command to run your binary
# CMD ["java", "-jar", "./app.jar"]

FROM openjdk:8u171-jre-alpine
VOLUME /tmp
ADD target/graphqltechdemo-*.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
CMD ["java", "-jar", "./app.jar"]
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]