FROM tomcat:9.0.65-jdk11
ADD target/ccrt_shop.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]