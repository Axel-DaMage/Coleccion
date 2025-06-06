package com.ecomarket.ecomarket.repository;

import com.ecomarket.ecomarket.model.Cliente;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    List<Cliente> findByRun(String run);

    Optional<Cliente> findByRunAndDv(String run, String dv);

    List<Cliente> findByNombresContainingIgnoreCase(String nombres);

    List<Cliente> findByApellidosContainingIgnoreCase(String apellidos);
}
