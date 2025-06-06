package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Compra;
import com.ecomarket.ecomarket.repository.CompraRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(int idCompra) {
        return compraRepository.findById(idCompra).orElse(null);
    }
}
