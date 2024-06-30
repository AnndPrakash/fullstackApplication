FROM openjdk:17
ADD target/sb_docker_app.jar sb_docker_app.jar
CMD java -jar sb_docker_app.jar
