package com.ecomarket.ecomarket.repository;

import com.ecomarket.ecomarket.model.Region;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    List<Region> findByNombreRegionContainingIgnoreCase(String nombreRegion);

    List<Region> findByIdRegion(int idRegion);
}
