FROM tomcat:10.1.34-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/ROOT*

COPY Conversa-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080