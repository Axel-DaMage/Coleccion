package com.ecomarket.ecomarket;

import com.ecomarket.ecomarket.model.*;
import com.ecomarket.ecomarket.repository.*;
import com.ecomarket.ecomarket.util.utils;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private DetalleRepository detalleRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ComunaRepository comunaRepository;
    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
    
    List<Region> regiones = Arrays.asList(
        new Region(2,"Antofagasta"),
        new Region(13,"Metropolitana de Santiago"),
        new Region(14,"Los Ríos")
    );
    for (Region region : regiones) {
        regionRepository.save(region);
    }

    // Generar Comunas
            
    List<Comuna> comunas = Arrays.asList(
        new Comuna(1, "Antofagasta", regiones.get(0)),
        new Comuna(2, "Mejillones", regiones.get(0)),
        new Comuna(3, "Sierra Gorda", regiones.get(0)),
        new Comuna(4, "Taltal", regiones.get(0)),
        new Comuna(2, "Santiago", regiones.get(1)),
        new Comuna(3, "Cerrillos", regiones.get(1)),
        new Comuna(4, "Cerro Navia", regiones.get(1)),
        new Comuna(5, "Conchalí", regiones.get(1)),
        new Comuna(6, "El Bosque", regiones.get(1)),
        new Comuna(7, "Estación Central", regiones.get(1)),
        new Comuna(8, "Huechuraba", regiones.get(1)),
        new Comuna(9, "Independencia", regiones.get(1)),
        new Comuna(10, "La Cisterna", regiones.get(1)),
        new Comuna(11, "La Florida", regiones.get(1)),
        new Comuna(12, "La Granja", regiones.get(1)),
        new Comuna(13, "La Pintana", regiones.get(1)),
        new Comuna(14, "La Reina", regiones.get(1)),
        new Comuna(15, "Las Condes", regiones.get(1)),
        new Comuna(16, "Lo Barnechea", regiones.get(1)),
        new Comuna(17, "Lo Espejo", regiones.get(1)),
        new Comuna(18, "Lo Prado", regiones.get(1)),
        new Comuna(19, "Macul", regiones.get(1)),
        new Comuna(20, "Maipú", regiones.get(1)),
        new Comuna(21, "Ñuñoa", regiones.get(1)),
        new Comuna(22, "Pedro Aguirre Cerda", regiones.get(1)),
        new Comuna(23, "Peñalolén", regiones.get(1)),
        new Comuna(24, "Providencia", regiones.get(1)),
        new Comuna(25, "Pudahuel", regiones.get(1)),
        new Comuna(26, "Quilicura", regiones.get(1)),
        new Comuna(27, "Quinta Normal", regiones.get(1)),
        new Comuna(28, "Recoleta", regiones.get(1)),
        new Comuna(29, "Renca", regiones.get(1)),
        new Comuna(30, "San Joaquín", regiones.get(1)),
        new Comuna(31, "San Miguel", regiones.get(1)),
        new Comuna(32, "San Ramón", regiones.get(1)),
        new Comuna(33, "Vitacura", regiones.get(1)),
        new Comuna(37, "Valdivia", regiones.get(2)),
        new Comuna(38, "Corral", regiones.get(2)),
        new Comuna(39, "Lanco", regiones.get(2)),
        new Comuna(40, "Los Lagos", regiones.get(2)),
        new Comuna(41, "Máfil", regiones.get(2)),
        new Comuna(42, "Mariquina", regiones.get(2)),
        new Comuna(43, "Paillaco", regiones.get(2)),
        new Comuna(44, "Panguipulli", regiones.get(2))
        );
        for (Comuna comuna : comunas) {
            comunaRepository.save(comuna);
        }

        // Generar Sucursales
        for (int i = 0; i < 10; i++) {
            Sucursal sucursal = new Sucursal();
            sucursal.setIdSucursal(i + 1);
            sucursal.setDireccionSucursal(faker.address().fullAddress());
            sucursal.setComuna(comunas.get(random.nextInt(comunas.size())));
            sucursalRepository.save(sucursal);
        }
        
        // Generar Productos
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setNombreProducto(faker.commerce().productName());
            producto.setStock(faker.number().numberBetween(0, 1000));
            productoRepository.save(producto);
        }

        // Generar Clientes
        for (int i = 0; i < 50; i++) {
            Cliente cliente = new Cliente();
            while(cliente.getRun() == null || cliente.getRun().isEmpty()){
                int rutnumerico = random.nextInt(9000000) + 1000000;
                String Stringrut = String.valueOf(rutnumerico);
                char dv = utils.calcularDv(rutnumerico);
                if (utils.esRutValido(Stringrut, dv)){
                    cliente.setRun(Stringrut);
                    cliente.setDv(dv);
                    cliente.setNombres(faker.name().firstName());
                    cliente.setApellidos(faker.name().lastName());
                    clienteRepository.save(cliente);}
                    break;
        }
    }

        // Generar compras
        List<Cliente> clientes = clienteRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Compra compra = new Compra();
            compra.setIdCompra(i + 1);
            compra.setFechaCompra(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS));
            compra.setCliente(clientes.get(random.nextInt(clientes.size())));
            compra.setNumeroFactura(String.valueOf(random.nextInt(999999999)));
            compra.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            compraRepository.save(compra);
        }

        // Generar Detalles
        List<Producto> productos = productoRepository.findAll();
        List<Compra> compras = compraRepository.findAll();
        for (int i = 0; i < 20; i++) {
            Detalle detalle = new Detalle();
            detalle.setCantidad(faker.number().numberBetween(1, 100));
            detalle.setPrecioUnitario(faker.number().numberBetween(1000, 100000));
            List<String> metodosPago = Arrays.asList("Efectivo", "Tarjeta", "Transferencia");
            detalle.setMetodoPago(metodosPago.get(random.nextInt(metodosPago.size())));
            detalle.setProducto(productos.get(random.nextInt(productos.size())));
            detalle.setCompra(compras.get(random.nextInt(compras.size())));
            detalleRepository.save(detalle);
        }
    }
}
