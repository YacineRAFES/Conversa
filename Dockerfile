FROM tomcat:10.1.34-jdk21-temurin

#ENV SPRING.DATASOURCE.URL=jdbc:mysql://mysql:3306/springboot
#ENV SPRING.DATASOURCE.USERNAME=root
#ENV SPRING.DATASOURCE.PASSWORD=root

COPY Conversa-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

#Exposer le port 8090
EXPOSE 8090