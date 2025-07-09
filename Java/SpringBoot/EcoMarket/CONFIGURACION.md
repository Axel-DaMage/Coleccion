# Guía de Configuración - EcoMarket

## Configuración de Base de Datos

### MySQL Setup
1. Instala MySQL 8.0 o superior
2. Crea las bases de datos necesarias:
```sql
CREATE DATABASE ecomarket;
CREATE DATABASE ecomarket_dev;
CREATE DATABASE ecomarket_test;
```

3. Crea un usuario específico para la aplicación:
```sql
CREATE USER 'tuuser'@'localhost' IDENTIFIED BY 'tucontraseña';
GRANT ALL PRIVILEGES ON ecomarket.* TO 'tuuser'@'localhost';
GRANT ALL PRIVILEGES ON ecomarket_dev.* TO 'tuuser'@'localhost';
GRANT ALL PRIVILEGES ON ecomarket_test.* TO 'tuuser'@'localhost';
FLUSH PRIVILEGES;
```

### Configuración de Variables de Entorno

```bash
export DB_USERNAME="tuuser"
export DB_PASSWORD="tucontraseña"
export DB_TEST_USERNAME="tuusertest"
export DB_TEST_PASSWORD="tucontraseñatest"

#### Windows (PowerShell)
```powershell
$env:DB_USERNAME="tuuser"
$env:DB_PASSWORD="tucontraseña"
$env:DB_TEST_USERNAME="tuusertest"
$env:DB_TEST_PASSWORD="tucontraseñatest"
```


#### Variables de Entorno Permanentes

##### Windows
1. Abrir "Variables de entorno del sistema"
2. Agregar las variables mencionadas arriba

## Configuración de Desarrollo

### IDE Setup (IntelliJ IDEA / Eclipse)
1. Importa el proyecto como proyecto Maven
2. Configura el JDK 17
3. Instala los plugins de Lombok
4. Configura las variables de entorno en la configuración de ejecución

### Configuración de Profiles
Para ejecutar con un perfil específico:

#### Línea de comandos
```bash
# Desarrollo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Producción (default)
./mvnw spring-boot:run

# Testing
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

### Ejemplo de configuración SSL (Producción)
```properties
# application-prod.properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecomarket?useSSL=true&requireSSL=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.keyStoreType=PKCS12
```

## Resolución de Problemas

#### Error de Conexión a Base de Datos
- Verifica que MySQL esté ejecutándose
- Confirma las credenciales de base de datos
- Revisa que las bases de datos existan

#### Error de Puerto en Uso
```bash
# Verificar qué proceso usa el puerto 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Cambiar puerto en application.properties
server.port=8081
```

#### Problemas con Lombok
- Instala el plugin de Lombok en tu IDE
- Habilita el procesamiento de anotaciones
- Reimporta el proyecto Maven

### Logs y Debugging
Para habilitar logs detallados:
```properties
# application-dev.properties
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## Monitoreo (Producción)

### Actuator Endpoints
Agregar dependencia:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Configuración:
```properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=when-authorized
```

### Métricas Disponibles
- `/actuator/health` - Estado de la aplicación
- `/actuator/info` - Información de la aplicación
- `/actuator/metrics` - Métricas de rendimiento
