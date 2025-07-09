package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.model.Sucursal;
import com.ecomarket.ecomarket.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService sucursalService; // Constants for link relations
    private static final String REL_SUCURSALES = "sucursales";
    private static final String REL_COMUNA = "comuna";

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @Operation(summary = "Obtener todas las sucursales", description = "Devuelve una lista de todas las sucursales registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida correctamente")
    @GetMapping
    public CollectionModel<EntityModel<Sucursal>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.findAll().stream()
                .map(this::addLinksToSucursal)
                .toList();
        return CollectionModel.of(sucursales)
                .add(linkTo(SucursalController.class).withSelfRel());
    }

    @Operation(summary = "Obtener sucursal por ID", description = "Devuelve una sucursal según su ID.")
    @ApiResponse(responseCode = "200", description = "Sucursal encontrada")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(
            @Parameter(description = "ID de la sucursal") @PathVariable Integer id) {
        Optional<Sucursal> sucursal = sucursalService.findById(id);
        return sucursal.map(this::addLinksToSucursal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva sucursal", description = "Crea una nueva sucursal.")
    @ApiResponse(responseCode = "200", description = "Sucursal creada correctamente")
    @PostMapping
    public EntityModel<Sucursal> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal savedSucursal = sucursalService.save(sucursal);
        return addLinksToSucursal(savedSucursal);
    }

    @Operation(summary = "Actualizar sucursal", description = "Actualiza los datos de una sucursal existente por ID.")
    @ApiResponse(responseCode = "200", description = "Sucursal actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(
            @Parameter(description = "ID de la sucursal a actualizar") @PathVariable Integer id,
            @RequestBody Sucursal sucursalDetails) {
        Optional<Sucursal> sucursal = sucursalService.findById(id);
        if (sucursal.isPresent()) {
            Sucursal existingSucursal = sucursal.get();
            existingSucursal.setDireccionSucursal(sucursalDetails.getDireccionSucursal());
            existingSucursal.setComuna(sucursalDetails.getComuna());
            Sucursal updatedSucursal = sucursalService.save(existingSucursal);
            return ResponseEntity.ok(addLinksToSucursal(updatedSucursal));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal por su ID.")
    @ApiResponse(responseCode = "204", description = "Sucursal eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(
            @Parameter(description = "ID de la sucursal a eliminar") @PathVariable Integer id) {
        if (sucursalService.findById(id).isPresent()) {
            sucursalService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private EntityModel<Sucursal> addLinksToSucursal(Sucursal sucursal) {
        EntityModel<Sucursal> sucursalModel = EntityModel.of(sucursal)
                .add(linkTo(SucursalController.class).slash(sucursal.getIdSucursal()).withSelfRel())
                .add(linkTo(SucursalController.class).withRel(REL_SUCURSALES));

        // Add link to comuna if available
        if (sucursal.getComuna() != null) {
            sucursalModel
                    .add(linkTo(ComunaController.class).slash(sucursal.getComuna().getIdComuna()).withRel(REL_COMUNA));
        }

        return sucursalModel;
    }
}
