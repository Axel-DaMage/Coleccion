package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.model.Producto;
import com.ecomarket.ecomarket.repository.ProductoRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }
    
    @GetMapping("/{idProducto}")
    public Producto getProductoById(@PathVariable int idProducto) {
        List<Producto> productos = productoRepository.findById(idProducto);
        return productos.isEmpty() ? null : productos.get(0);
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/{idProducto}")
    public Producto updateProducto(@PathVariable int idProducto, @RequestBody Producto producto) {
        if (productoRepository.existsById(idProducto)) {
            producto.setIdProducto(idProducto);
            return productoRepository.save(producto);
        }
        return null;
    }

    @DeleteMapping("/{idProducto}")
    public void deleteProducto(@PathVariable int idProducto) {
        productoRepository.deleteById(idProducto);
    }
}
