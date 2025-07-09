package com.ecomarket.ecomarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "COMUNA")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

public class Comuna extends RepresentationModel<Comuna> {
    @Id
    @Column(name = "ID_COMUNA")
    private int idComuna;

    @Column(name = "NOMBRE_COMUNA", nullable = false, length = 40)
    private String nombreComuna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_REGION", nullable = false)
    private Region region;
}
