FROM tomcat:9.0-jdk17

COPY SriHavensWeb /usr/local/tomcat/webapps/ROOT

EXPOSE 8080

CMD ["catalina.sh", "run"]
