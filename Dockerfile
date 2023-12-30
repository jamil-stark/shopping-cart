FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar shop.jar
ENTRYPOINT ["java","-jar","/shop.jar"]
EXPOSE 8080