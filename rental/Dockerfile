FROM openjdk:11
ADD target/spring-boot-docker-rental.jar spring-boot-docker-rental.jar
ENTRYPOINT ["java", "-jar", "spring-boot-docker-rental.jar"]
EXPOSE 8002

#FROM openjdk:11-jdk as builder
#WORKDIR application
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract

#FROM openjdk:11-jdk
#ENV PORT=8002
#WORKDIR application
#COPY --from=builder application/spring-boot-loader/ ./
#COPY --from=builder application/dependencies/ ./
#COPY --from=builder application/snapshot-dependencies/ ./
#COPY --from=builder application/application/ ./
#ENTRYPOINT ["java","-Dserver.port=${PORT:-8002}", "org.springframework.boot.loader.JarLauncher"]