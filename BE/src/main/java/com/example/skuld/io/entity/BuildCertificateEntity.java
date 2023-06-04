package com.example.skuld.io.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "build_certificate")
public class BuildCertificateEntity {

    public BuildCertificateEntity() {

    }
    public BuildCertificateEntity(Long id, VesselEntity vesselEntity, String type, LocalDate validFrom, LocalDate validTo) {

        this.id= id;
        this.vesselEntity = vesselEntity;
        this.type = type;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", nullable = false)
    private VesselEntity vesselEntity;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VesselEntity getVessel() {
        return vesselEntity;
    }

    public VesselEntity getVesselEntity() {
        return vesselEntity;
    }

    public void setVesselEntity(VesselEntity vesselEntity) {
        this.vesselEntity = vesselEntity;
    }

    public void setVessel(VesselEntity vesselEntity) {
        this.vesselEntity = vesselEntity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}