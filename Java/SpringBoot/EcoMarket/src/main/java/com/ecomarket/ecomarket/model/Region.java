package com.ecomarket.ecomarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "REGION")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

public class Region extends RepresentationModel<Region> {
    @Id
    @Column(name = "ID_REGION")
    private int idRegion;

    @Column(name = "NOMBRE_REGION", nullable = false, length = 40)
    private String nombreRegion;
}
