package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Detalle;
import com.ecomarket.ecomarket.repository.DetalleRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DetalleService {

    private final DetalleRepository detalleRepository;

    public DetalleService(DetalleRepository detalleRepository) {
        this.detalleRepository = detalleRepository;
    }

    public List<Detalle> getAllDetalles() {
        return detalleRepository.findAll();
    }
}