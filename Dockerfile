FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/coffee_shop-1.0-SNAPSHOT.jar coffee_shop.jar
ADD target/classes/db src/main/resources/db
ADD src/main/webapp src/main/webapp
ADD images images
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/coffee_shop.jar"]