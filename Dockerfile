FROM tomcat:10.1.34-jdk21-temurin

#ENV SPRING.DATASOURCE.URL=jdbc:mysql://mysql:3306/springboot
#ENV SPRING.DATASOURCE.USERNAME=root
#ENV SPRING.DATASOURCE.PASSWORD=root

WORKDIR /app

#Copie du ficher JAR recuperer de l'artefact de votre projet dans le conteneur
COPY target/Conversa-1.0-SNAPSHOT.war /app/Conversa-1.0-SNAPSHOT.war

#Exposer le port 8090
EXPOSE 8090


ENTRYPOINT ["java", "-war", "Conversa-1.0-SNAPSHOT.war"]
