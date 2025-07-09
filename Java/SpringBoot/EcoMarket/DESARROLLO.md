# Guía de Desarrollo - EcoMarket

## Estructura del Código

### Arquitectura por Capas

El proyecto sigue el patrón de arquitectura por capas de Spring Boot:

```
Controller → Service → Repository → Entity
```

### Paquetes Principales

#### `com.ecomarket.ecomarket.model`
Contiene las entidades JPA que representan las tablas de la base de datos.

**Entidades principales:**
- `Cliente.java` - Información de clientes
- `Producto.java` - Catálogo de productos
- `Compra.java` - Transacciones de compra
- `Detalle.java` - Detalles de cada compra
- `Sucursal.java` - Ubicaciones de tiendas
- `Comuna.java` - División administrativa
- `Region.java` - División geográfica mayor

#### `com.ecomarket.ecomarket.repository`
Interfaces que extienden `JpaRepository` para operaciones CRUD.

**Características:**
- Métodos de consulta personalizados usando `@Query`
- Named queries para consultas complejas
- Paginación y ordenamiento automático

#### `com.ecomarket.ecomarket.service`
Lógica de negocio de la aplicación.

**Responsabilidades:**
- Validaciones de negocio
- Transformación de datos
- Coordinación entre repositorios
- Manejo de transacciones

#### `com.ecomarket.ecomarket.controller`
Controladores REST que exponen los endpoints de la API.

**Características:**
- Anotaciones Spring Web
- Validación de entrada
- Manejo de respuestas HTTP
- Documentación Swagger

#### `com.ecomarket.ecomarket.config`
Configuraciones de Spring y beans personalizados.

#### `com.ecomarket.ecomarket.exception`
Manejo centralizado de excepciones.

#### `com.ecomarket.ecomarket.util`
Utilidades comunes del proyecto.

**Incluye:**
- Validación de RUT chileno
- Cálculo de dígito verificador
- Utilidades de fecha y formato

## Convenciones de Código

### Nomenclatura

#### Clases
- **Entidades**: Nombres singulares (ej: `Cliente`, `Producto`)
- **Repositorios**: `[Entidad]Repository` (ej: `ClienteRepository`)
- **Servicios**: `[Entidad]Service` (ej: `ClienteService`)
- **Controladores**: `[Entidad]Controller` (ej: `ClienteController`)

#### Métodos
- **Repositorios**: `findBy[Campo]`, `existsBy[Campo]`
- **Servicios**: Verbos descriptivos (`obtenerTodos`, `crear`, `actualizar`)
- **Controladores**: Nombres REST (`getAllClientes`, `getClienteByRun`)

#### Variables
- camelCase para variables locales
- SNAKE_CASE para constantes
- Nombres descriptivos y en español

### Anotaciones Importantes

#### JPA
```java
@Entity
@Table(name = "CLIENTE")
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "RUN", length = 12)
@ManyToOne
@JoinColumn(name = "ID_SUCURSAL")
```

#### Spring Web
```java
@RestController
@RequestMapping("/api/clientes")
@GetMapping("/{run}")
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
@Valid
```

#### Validación
```java
@NotNull
@NotBlank
@Size(max = 50)
@Pattern(regexp = "^[0-9]{7,8}$")
```

## Desarrollo de Nuevas Funcionalidades

### 1. Agregar Nueva Entidad

#### Paso 1: Crear la Entidad
```java
@Entity
@Table(name = "NUEVA_ENTIDAD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CAMPO_EJEMPLO")
    private String campoEjemplo;
}
```

#### Paso 2: Crear el Repository
```java
@Repository
public interface NuevaEntidadRepository extends JpaRepository<NuevaEntidad, Long> {
    List<NuevaEntidad> findByCampoEjemplo(String valor);
}
```

#### Paso 3: Crear el Service
```java
@Service
@Transactional
public class NuevaEntidadService {
    
    @Autowired
    private NuevaEntidadRepository repository;
    
    public List<NuevaEntidad> obtenerTodas() {
        return repository.findAll();
    }
    
    public NuevaEntidad crear(NuevaEntidad entidad) {
        return repository.save(entidad);
    }
}
```

#### Paso 4: Crear el Controller
```java
@RestController
@RequestMapping("/api/nueva-entidad")
public class NuevaEntidadController {
    
    @Autowired
    private NuevaEntidadService service;
    
    @GetMapping
    public ResponseEntity<List<NuevaEntidad>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NuevaEntidad crear(@Valid @RequestBody NuevaEntidad entidad) {
        return service.crear(entidad);
    }
}
```

### 2. Agregar Validaciones Personalizadas

#### Crear Anotación de Validación
```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RutValidator.class)
public @interface ValidRut {
    String message() default "RUT inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

#### Implementar Validador
```java
public class RutValidator implements ConstraintValidator<ValidRut, String> {
    @Override
    public boolean isValid(String rut, ConstraintValidatorContext context) {
        // Lógica de validación
        return utils.esRutValido(rut);
    }
}
```

### 3. Agregar Tests

#### Test de Repository
```java
@DataJpaTest
class ClienteRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private ClienteRepository repository;
    
    @Test
    void whenFindByRun_thenReturnCliente() {
        // Given
        Cliente cliente = new Cliente("12345678", "9", "Juan", "Pérez");
        entityManager.persist(cliente);
        entityManager.flush();
        
        // When
        Optional<Cliente> found = repository.findByRun("12345678");
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getNombres()).isEqualTo("Juan");
    }
}
```

#### Test de Service
```java
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    
    @Mock
    private ClienteRepository repository;
    
    @InjectMocks
    private ClienteService service;
    
    @Test
    void whenValidRun_thenClienteShouldBeFound() {
        // Given
        Cliente cliente = new Cliente("12345678", "9", "Juan", "Pérez");
        when(repository.findByRun("12345678")).thenReturn(Optional.of(cliente));
        
        // When
        Cliente found = service.obtenerPorRun("12345678");
        
        // Then
        assertThat(found.getNombres()).isEqualTo("Juan");
    }
}
```

#### Test de Controller
```java
@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
class ClienteControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void whenGetClientes_thenStatus200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/clientes", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
```

## Best Practices

### 1. Manejo de Excepciones
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("Recurso no encontrado", ex.getMessage()));
    }
}
```

### 2. DTOs para API
```java
public class ClienteDTO {
    private String run;
    private String nombres;
    private String apellidos;
    // Constructor, getters, setters
}
```

### 3. Mapeo de Entidades a DTOs
```java
@Component
public class ClienteMapper {
    
    public ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(
            cliente.getRun(),
            cliente.getNombres(),
            cliente.getApellidos()
        );
    }
    
    public Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
            dto.getRun(),
            utils.calcularDv(dto.getRun()),
            dto.getNombres(),
            dto.getApellidos()
        );
    }
}
```

### 4. Transacciones
```java
@Service
@Transactional
public class CompraService {
    
    @Transactional(readOnly = true)
    public List<Compra> obtenerTodas() {
        return repository.findAll();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Compra crearCompraCompleta(CompraDTO compraDTO, List<DetalleDTO> detalles) {
        // Lógica compleja que requiere transacción
    }
}
```

## Herramientas de Desarrollo

### Maven Commands
```bash
# Compilar
./mvnw compile

# Ejecutar tests
./mvnw test

# Empaquetar
./mvnw package

# Limpiar y compilar
./mvnw clean compile

# Ejecutar aplicación
./mvnw spring-boot:run

# Ejecutar con perfil específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Debugging
- Configurar breakpoints en IDE
- Usar logging para seguimiento
- Activar SQL logging en desarrollo:
  ```properties
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.format_sql=true
  ```

### Perfiles de Configuración
- **dev**: Desarrollo local con logs detallados
- **test**: Testing automatizado con datos de prueba
- **prod**: Producción con configuración optimizada

## Extensiones Recomendadas

### Para el Proyecto
- Swagger para documentación API
- MapStruct para mapeo de objetos
- Spring Security para autenticación
- Redis para caché
- Actuator para monitoreo

### Para Desarrollo
- Spring Boot DevTools para hot reload
- H2 Database para tests rápidos
- Testcontainers para tests de integración
- JaCoCo para cobertura de código
