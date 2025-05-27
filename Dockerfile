FROM tomcat:10.1.34-jdk21-temurin

#ENV SPRING.DATASOURCE.URL=jdbc:mysql://mysql:3306/springboot
#ENV SPRING.DATASOURCE.USERNAME=root
#ENV SPRING.DATASOURCE.PASSWORD=root

#Exposer le port 8090
EXPOSE 8090

#Copie du ficher JAR recuperer de l'artefact de votre projet dans le conteneur
COPY Conversa-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/conversaweb.war

#Commande pour exécuter le fichier JAR
CMD ["java", "-war", "Conversa-1.0-SNAPSHOT.war"]