package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Region;
import com.ecomarket.ecomarket.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getAllRegiones() {
        return regionRepository.findAll();
    }

    public Optional<Region> getRegionById(int idRegion) {
        return regionRepository.findById(idRegion);
    }

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    public void deleteRegion(int idRegion) {
        regionRepository.deleteById(idRegion);
    }

    public List<Region> findByNombre(String nombreRegion) {
        return regionRepository.findByNombreRegionContainingIgnoreCase(nombreRegion);
    }
}
