# Documentación de API - EcoMarket

## Información General

**Base URL**: `http://localhost:8080/api`
**Formato**: JSON
**Autenticación**: No requerida (proyecto de demostración)

## Endpoints Disponibles

### 1. Gestión de Clientes

#### GET /api/clientes
Obtiene la lista de todos los clientes.

**Respuesta**:
```json
[
  {
    "run": "12345678",
    "dv": "9",
    "nombres": "Juan Carlos",
    "apellidos": "González Pérez"
  }
]
```

#### GET /api/clientes/{run}
Obtiene un cliente específico por RUN.

**Parámetros**:
- `run` (string): RUN del cliente sin dígito verificador

#### POST /api/clientes
Crea un nuevo cliente.

**Cuerpo de la petición**:
```json
{
  "run": "12345678",
  "dv": "9",
  "nombres": "Juan Carlos",
  "apellidos": "González Pérez"
}
```

#### PUT /api/clientes/{run}
Actualiza un cliente existente.

#### DELETE /api/clientes/{run}
Elimina un cliente.

### 2. Gestión de Productos

#### GET /api/productos
Obtiene la lista de todos los productos.

**Respuesta**:
```json
[
  {
    "idProducto": 1,
    "nombreProducto": "Manzanas Orgánicas",
    "stock": 150
  }
]
```

#### GET /api/productos/{id}
Obtiene un producto específico.

#### POST /api/productos
Crea un nuevo producto.

**Cuerpo de la petición**:
```json
{
  "nombreProducto": "Manzanas Orgánicas",
  "stock": 150
}
```

#### PUT /api/productos/{id}
Actualiza un producto existente.

#### DELETE /api/productos/{id}
Elimina un producto.

### 3. Gestión de Sucursales

#### GET /api/sucursales
Obtiene la lista de todas las sucursales.

**Respuesta**:
```json
[
  {
    "idSucursal": 1,
    "direccionSucursal": "Av. Providencia 1234, Santiago",
    "comuna": {
      "idComuna": 24,
      "nombreComuna": "Providencia",
      "region": {
        "idRegion": 13,
        "nombreRegion": "Metropolitana de Santiago"
      }
    }
  }
]
```

#### GET /api/sucursales/{id}
Obtiene una sucursal específica.

#### POST /api/sucursales
Crea una nueva sucursal.

#### PUT /api/sucursales/{id}
Actualiza una sucursal existente.

#### DELETE /api/sucursales/{id}
Elimina una sucursal.

### 4. Gestión de Compras

#### GET /api/compras
Obtiene la lista de todas las compras.

**Respuesta**:
```json
[
  {
    "idCompra": 1,
    "fechaCompra": "2025-07-08",
    "numeroFactura": "FAC001234",
    "cliente": {
      "run": "12345678",
      "dv": "9",
      "nombres": "Juan Carlos",
      "apellidos": "González Pérez"
    },
    "sucursal": {
      "idSucursal": 1,
      "direccionSucursal": "Av. Providencia 1234, Santiago"
    }
  }
]
```

#### GET /api/compras/{id}
Obtiene una compra específica.

#### GET /api/compras/cliente/{run}
Obtiene todas las compras de un cliente específico.

#### GET /api/compras/fecha
Obtiene compras por rango de fechas.

**Parámetros de consulta**:
- `fechaInicio` (date): Fecha inicial (formato: YYYY-MM-DD)
- `fechaFin` (date): Fecha final (formato: YYYY-MM-DD)

#### POST /api/compras
Crea una nueva compra.

**Cuerpo de la petición**:
```json
{
  "fechaCompra": "2025-07-08",
  "numeroFactura": "FAC001234",
  "clienteRun": "12345678",
  "idSucursal": 1
}
```

### 5. Gestión de Detalles de Compra

#### GET /api/detalles
Obtiene todos los detalles de compra.

**Respuesta**:
```json
[
  {
    "idDetalle": 1,
    "cantidad": 3,
    "precioUnitario": 2500,
    "metodoPago": "Tarjeta",
    "compra": {
      "idCompra": 1,
      "fechaCompra": "2025-07-08"
    },
    "producto": {
      "idProducto": 1,
      "nombreProducto": "Manzanas Orgánicas"
    }
  }
]
```

#### GET /api/detalles/{id}
Obtiene un detalle específico.

#### GET /api/detalles/compra/{idCompra}
Obtiene todos los detalles de una compra específica.

#### POST /api/detalles
Crea un nuevo detalle de compra.

**Cuerpo de la petición**:
```json
{
  "cantidad": 3,
  "precioUnitario": 2500,
  "metodoPago": "Tarjeta",
  "idCompra": 1,
  "idProducto": 1
}
```

### 6. Gestión de Regiones y Comunas

#### GET /api/regiones
Obtiene todas las regiones.

#### GET /api/regiones/{id}
Obtiene una región específica.

#### GET /api/comunas
Obtiene todas las comunas.

#### GET /api/comunas/{id}
Obtiene una comuna específica.

#### GET /api/comunas/region/{idRegion}
Obtiene todas las comunas de una región específica.

## Códigos de Estado HTTP

- `200 OK`: Operación exitosa
- `201 Created`: Recurso creado exitosamente
- `400 Bad Request`: Datos de entrada inválidos
- `404 Not Found`: Recurso no encontrado
- `409 Conflict`: Conflicto (ej: RUN duplicado)
- `500 Internal Server Error`: Error interno del servidor

## Formatos de Fecha

Todas las fechas se manejan en formato ISO 8601: `YYYY-MM-DD`

## Validaciones

### Cliente
- RUN debe ser válido según algoritmo chileno
- Dígito verificador debe corresponder al RUN
- Nombres y apellidos son obligatorios

### Producto
- Nombre del producto es obligatorio
- Stock debe ser un número entero no negativo

### Compra
- Fecha de compra es obligatoria
- Número de factura debe ser único
- Cliente debe existir
- Sucursal debe existir

### Detalle
- Cantidad debe ser mayor a 0
- Precio unitario debe ser mayor a 0
- Método de pago debe ser válido (Efectivo, Tarjeta, Transferencia)
- Compra y producto deben existir

## Ejemplos de Uso

### Crear una compra completa con detalles

1. **Crear cliente**:
```bash
POST /api/clientes
{
  "run": "19876543",
  "dv": "2",
  "nombres": "María Elena",
  "apellidos": "Rodríguez Silva"
}
```

2. **Crear compra**:
```bash
POST /api/compras
{
  "fechaCompra": "2025-07-08",
  "numeroFactura": "FAC001235",
  "clienteRun": "19876543",
  "idSucursal": 1
}
```

3. **Agregar detalles**:
```bash
POST /api/detalles
{
  "cantidad": 2,
  "precioUnitario": 3500,
  "metodoPago": "Efectivo",
  "idCompra": 2,
  "idProducto": 1
}
```

### Consultar compras de un cliente
```bash
GET /api/compras/cliente/19876543
```

### Buscar compras por fecha
```bash
GET /api/compras/fecha?fechaInicio=2025-07-01&fechaFin=2025-07-08
```

## Swagger UI

Para una documentación interactiva completa, visita:
`http://localhost:8080/swagger-ui/index.html`

Allí podrás:
- Ver todos los endpoints disponibles
- Probar las APIs directamente
- Ver esquemas de datos detallados
- Descargar la especificación OpenAPI
