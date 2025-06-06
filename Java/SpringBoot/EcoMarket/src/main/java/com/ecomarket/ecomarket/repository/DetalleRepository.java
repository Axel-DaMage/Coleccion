package com.ecomarket.ecomarket.repository;

import com.ecomarket.ecomarket.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Integer> {

    List<Detalle> findById(int idDetalle);
    
    List<Detalle> findByCantidad(int cantidad);

    List<Detalle> findByPrecioUnitario(BigDecimal precioUnitario);

    List<Detalle> findByMetodoPago(String metodoPago);

    List<Detalle> findByCompraIdCompra(int idCompra);

    List<Detalle> findByProductoIdProducto(int idProducto);
}