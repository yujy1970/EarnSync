修改  application.properties

server:
  address: 0.0.0.0
  port: 8080


netsh advfirewall firewall add rule name="Spring Boot Tomcat" dir=in action=allow protocol=TCP localport=8080