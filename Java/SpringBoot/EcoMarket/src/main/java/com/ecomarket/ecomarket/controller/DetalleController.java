package com.ecomarket.ecomarket.controller;


import com.ecomarket.ecomarket.model.Detalle;
import com.ecomarket.ecomarket.repository.DetalleRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles")
public class DetalleController {

    @Autowired
    private DetalleRepository detalleRepository;

    @GetMapping
    public List<Detalle> getAllDetalles() {
        return detalleRepository.findAll();
    }

    @GetMapping("/{idDetalle}")
    public Detalle getDetalleById(@PathVariable int idDetalle) {
        List<Detalle> detalles = detalleRepository.findById(idDetalle);
        return detalles.isEmpty() ? null : detalles.get(0);
    }

    @PostMapping
    public Detalle createDetalle(@RequestBody Detalle detalle) {
        return detalleRepository.save(detalle);
    }

    @PutMapping("/{idDetalle}")
    public Detalle updateDetalle(@PathVariable int idDetalle, @RequestBody Detalle detalle) {
        if (detalleRepository.existsById(idDetalle)) {
            detalle.setIdDetalle(idDetalle);
            return detalleRepository.save(detalle);
        }
        return null;
    }

    @DeleteMapping("/{idDetalle}")
    public void deleteDetalle(@PathVariable int idDetalle) {
        detalleRepository.deleteById(idDetalle);
    }
}
