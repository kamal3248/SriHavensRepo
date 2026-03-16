FROM tomcat:9.0-jdk17

COPY SriHavensWeb /usr/local/tomcat/webapps/SriHavensWeb

EXPOSE 8080

CMD ["catalina.sh", "run"]
