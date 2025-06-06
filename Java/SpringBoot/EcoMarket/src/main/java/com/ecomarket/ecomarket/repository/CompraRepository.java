package com.ecomarket.ecomarket.repository;

import com.ecomarket.ecomarket.model.Compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    List<Compra> findByIdCompra(int idCompra);

    List<Compra> findByFechaCompra(Date fechaCompra);

    List<Compra> findByFechaCompraBetween(Date fechaInicio, Date fechaFin);
}