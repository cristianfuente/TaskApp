FROM openjdk:21-jdk

# Argumento para el nombre del archivo JAR
ARG JAR_FILE=task/target/task.jar

# Copia el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que corre SpringBoot 
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]

