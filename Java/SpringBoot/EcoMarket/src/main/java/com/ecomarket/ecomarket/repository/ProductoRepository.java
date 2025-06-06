package com.ecomarket.ecomarket.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.math.BigDecimal;


import com.ecomarket.ecomarket.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findById(int idProducto);

    List<Producto> findByStock(int stock);

    List<Producto> findByPrecio(BigDecimal precio);

    List<Producto> findByNombreProducto(String nombreProducto);
}