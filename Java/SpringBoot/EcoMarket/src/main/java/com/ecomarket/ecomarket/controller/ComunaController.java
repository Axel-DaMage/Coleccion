package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.model.Comuna;
import com.ecomarket.ecomarket.service.ComunaService;
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
@RequestMapping("/api/comunas")
public class ComunaController {
    private final ComunaService comunaService;

    // Constants for link relations
    private static final String REL_COMUNAS = "comunas";
    private static final String REL_REGION = "region";

    public ComunaController(ComunaService comunaService) {
        this.comunaService = comunaService;
    }

    @Operation(summary = "Obtener todas las comunas", description = "Devuelve una lista de todas las comunas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de comunas obtenida correctamente")
    @GetMapping
    public CollectionModel<EntityModel<Comuna>> getAllComunas() {
        List<EntityModel<Comuna>> comunas = comunaService.findAll().stream()
                .map(this::addLinksToComuna)
                .toList();

        return CollectionModel.of(comunas)
                .add(linkTo(ComunaController.class).withSelfRel());
    }

    @Operation(summary = "Obtener comuna por ID", description = "Devuelve una comuna según su ID.")
    @ApiResponse(responseCode = "200", description = "Comuna encontrada")
    @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Comuna>> getComunaById(
            @Parameter(description = "ID de la comuna") @PathVariable Integer id) {
        Optional<Comuna> comuna = comunaService.findById(id);
        return comuna.map(this::addLinksToComuna)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva comuna", description = "Crea una nueva comuna.")
    @ApiResponse(responseCode = "200", description = "Comuna creada correctamente")
    @PostMapping
    public EntityModel<Comuna> createComuna(@RequestBody Comuna comuna) {
        Comuna savedComuna = comunaService.save(comuna);
        return addLinksToComuna(savedComuna);
    }

    @Operation(summary = "Actualizar comuna", description = "Actualiza los datos de una comuna existente por ID.")
    @ApiResponse(responseCode = "200", description = "Comuna actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Comuna>> updateComuna(
            @Parameter(description = "ID de la comuna a actualizar") @PathVariable Integer id,
            @RequestBody Comuna comunaDetails) {
        Optional<Comuna> comuna = comunaService.findById(id);
        if (comuna.isPresent()) {
            Comuna existingComuna = comuna.get();
            existingComuna.setNombreComuna(comunaDetails.getNombreComuna());
            existingComuna.setRegion(comunaDetails.getRegion());
            Comuna updatedComuna = comunaService.save(existingComuna);
            return ResponseEntity.ok(addLinksToComuna(updatedComuna));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna por su ID.")
    @ApiResponse(responseCode = "204", description = "Comuna eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComuna(
            @Parameter(description = "ID de la comuna a eliminar") @PathVariable Integer id) {
        if (comunaService.findById(id).isPresent()) {
            comunaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar comunas por nombre", description = "Busca comunas por su nombre.")
    @ApiResponse(responseCode = "200", description = "Lista de comunas encontradas")
    @GetMapping("/buscar")
    public CollectionModel<EntityModel<Comuna>> findByNombre(
            @Parameter(description = "Nombre de la comuna a buscar") @RequestParam String nombre) {
        List<EntityModel<Comuna>> comunas = comunaService.findByNombre(nombre).stream()
                .map(this::addLinksToComuna)
                .toList();
        return CollectionModel.of(comunas)
                .add(linkTo(ComunaController.class).slash("buscar").withSelfRel())
                .add(linkTo(ComunaController.class).withRel(REL_COMUNAS));
    }

    @Operation(summary = "Buscar comunas por región", description = "Busca comunas según el ID de la región.")
    @ApiResponse(responseCode = "200", description = "Lista de comunas encontradas por región")
    @GetMapping("/region/{idRegion}")
    public CollectionModel<EntityModel<Comuna>> findByRegionId(@PathVariable int idRegion) {
        List<EntityModel<Comuna>> comunas = comunaService.findByRegionId(idRegion).stream()
                .map(this::addLinksToComuna)
                .toList();
        return CollectionModel.of(comunas)
                .add(linkTo(ComunaController.class).slash(REL_REGION).slash(idRegion).withSelfRel())
                .add(linkTo(ComunaController.class).withRel(REL_COMUNAS));
    }

    private EntityModel<Comuna> addLinksToComuna(Comuna comuna) {
        EntityModel<Comuna> comunaModel = EntityModel.of(comuna)
                .add(linkTo(ComunaController.class).slash(comuna.getIdComuna()).withSelfRel())
                .add(linkTo(ComunaController.class).withRel(REL_COMUNAS));

        // Add region link if region is not null
        if (comuna.getRegion() != null) {
            comunaModel.add(linkTo(ComunaController.class).slash(REL_REGION).slash(comuna.getRegion().getIdRegion())
                    .withRel(REL_REGION));
        }

        return comunaModel;
    }
}
