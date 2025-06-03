FROM tomcat:10.1.34-jdk21-temurin

#ENV SPRING.DATASOURCE.URL=jdbc:mysql://mysql:3306/springboot
#ENV SPRING.DATASOURCE.USERNAME=root
#ENV SPRING.DATASOURCE.PASSWORD=root

#Copie du ficher JAR recuperer de l'artefact de votre projet dans le conteneur
COPY target/Conversa-1.0-SNAPSHOT.war app.war

#Exposer le port 8090
EXPOSE 8090