# EcoMarket - Sistema de Gestión de Tienda Ecológica

## Descripción
EcoMarket es una aplicación Spring Boot que proporciona una API REST para la gestión de un sistema de tienda ecológica. El sistema permite administrar clientes, productos, compras, sucursales y detalles de transacciones.

## Tecnologías Utilizadas
- **Java 17**
- **Spring Boot 3.3.11**
- **Spring Data JPA**
- **MySQL Database**
- **Maven** para gestión de dependencias
- **Lombok** para reducción de código boilerplate
- **DataFaker** para generación de datos de prueba
- **Spring Boot Test** para testing

## Estructura del Proyecto

### Modelos de Datos
- **Region**: Gestión de regiones geográficas
- **Comuna**: Gestión de comunas por región
- **Cliente**: Información de clientes (RUN, nombres, apellidos)
- **Producto**: Catálogo de productos con stock
- **Sucursal**: Ubicaciones de tiendas
- **Compra**: Registro de transacciones de compra
- **Detalle**: Detalles específicos de cada compra

### Estructura de Directorios
```
src/
├── main/
│   ├── java/com/ecomarket/ecomarket/
│   │   ├── config/           # Configuraciones de Spring
│   │   ├── controller/       # Controladores REST
│   │   ├── exception/        # Manejo de excepciones
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositorios de datos
│   │   ├── service/         # Lógica de negocio
│   │   ├── util/            # Utilidades (validación RUT, etc.)
│   │   ├── DataLoader.java  # Carga de datos de prueba
│   │   └── EcomarketApplication.java # Clase principal
│   └── resources/
│       ├── static/          # Archivos estáticos (HTML, CSS)
│       ├── application.properties      # Configuración principal
│       ├── application-dev.properties  # Configuración desarrollo
│       └── application-test.properties # Configuración testing
└── test/                    # Tests unitarios e integración
```

## Configuración

### Base de Datos
El proyecto está configurado para usar MySQL. Debes crear las siguientes bases de datos:
- `ecomarket` (producción)
- `ecomarket_dev` (desarrollo)
- `ecomarket_test` (pruebas)

### Variables de Entorno
Para mayor seguridad, configure las siguientes variables de entorno:
- `DB_USERNAME`: Usuario de base de datos
- `DB_PASSWORD`: Contraseña de base de datos
- `DB_TEST_USERNAME`: Usuario para base de datos de pruebas
- `DB_TEST_PASSWORD`: Contraseña para base de datos de pruebas

### Perfiles de Spring
- **default**: Configuración de producción
- **dev**: Configuración de desarrollo con logging detallado
- **test**: Configuración de pruebas con carga automática de datos

## Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+

### Pasos
1. Clona el repositorio
2. Configura las bases de datos MySQL
3. Establece las variables de entorno o modifica los archivos de propiedades
4. Ejecuta el script SQL para crear las tablas: `ecomarket.SQL`
5. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

### Ejecutar con perfil específico
```bash
# Desarrollo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Pruebas
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

## API Endpoints

La aplicación expone varios endpoints REST para gestionar las entidades del sistema. 

### Documentación Swagger
Una vez que la aplicación esté ejecutándose, puedes acceder a:
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **Interfaz Web**: `http://localhost:8080/`

### Principales Endpoints
- `/api/clientes` - Gestión de clientes
- `/api/productos` - Gestión de productos
- `/api/compras` - Gestión de compras
- `/api/detalles` - Gestión de detalles de compra
- `/api/sucursales` - Gestión de sucursales
- `/api/comunas` - Gestión de comunas
- `/api/regiones` - Gestión de regiones

## Testing

### Ejecutar Tests
```bash
# Todos los tests
./mvnw test

# Tests específicos
./mvnw test -Dtest=ClienteServiceTest
```

### Datos de Prueba
Al ejecutar con el perfil `test`, se cargan automáticamente datos de ejemplo incluyendo:
- Regiones y comunas de Chile
- Clientes con RUT válidos
- Productos variados
- Sucursales distribuidas
- Compras y detalles de ejemplo

## Funcionalidades Destacadas

### Validación de RUT
- Implementación de algoritmo de validación de RUT chileno
- Generación automática de dígito verificador
- Validación en la carga de datos

### Generación de Datos
- Uso de DataFaker para crear datos realistas
- Población automática de base de datos en perfil test
- Datos coherentes entre entidades relacionadas

### Arquitectura REST
- Controladores RESTful bien estructurados
- Manejo centralizado de excepciones
- Separación clara entre capas (Controller → Service → Repository)

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia especificada en el archivo de licencia.

## Contacto

Para preguntas o sugerencias sobre el proyecto, por favor abre un issue en el repositorio.
