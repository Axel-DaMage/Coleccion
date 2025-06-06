package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.model.Compra;
import com.ecomarket.ecomarket.repository.CompraRepository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraRepository compraRepository;

    public CompraController(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @GetMapping
    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    @GetMapping("/{idCompra}")
    public ResponseEntity<Compra> getCompraById(@PathVariable int idCompra) {
        List<Compra> compras = compraRepository.findByIdCompra(idCompra);
        if (compras != null && !compras.isEmpty()) {
            return ResponseEntity.ok(compras.get(0));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Compra createCompra(@RequestBody Compra compra) {
        return compraRepository.save(compra);
    }

    @PutMapping("/{idCompra}")
    public Compra updateCompra(@PathVariable Integer idCompra, @RequestBody Compra compra) {
        if (compraRepository.existsById(idCompra)) {
            compra.setIdCompra(idCompra.intValue());
            return compraRepository.save(compra);
        }
        return null;
    }

    @DeleteMapping("/{idCompra}")
    public void deleteCompra(@PathVariable Integer idCompra) {
        compraRepository.deleteById(idCompra);
    }
}
