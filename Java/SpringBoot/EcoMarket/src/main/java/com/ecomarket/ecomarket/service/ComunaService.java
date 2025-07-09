package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Comuna;
import com.ecomarket.ecomarket.repository.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Optional<Comuna> findById(Integer id) {
        return comunaRepository.findById(id);
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public void deleteById(Integer id) {
        comunaRepository.deleteById(id);
    }

    public List<Comuna> findByNombre(String nombreComuna) {
        return comunaRepository.findByNombreComunaContainingIgnoreCase(nombreComuna);
    }

    public List<Comuna> findByRegionId(int idRegion) {
        return comunaRepository.findByRegion_IdRegion(idRegion);
    }
}
