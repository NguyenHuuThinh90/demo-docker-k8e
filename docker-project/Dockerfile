FROM openjdk:11-oracle
COPY ./target/webapp.jar /webapp.jar
VOLUME /file
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/webapp.jar"]