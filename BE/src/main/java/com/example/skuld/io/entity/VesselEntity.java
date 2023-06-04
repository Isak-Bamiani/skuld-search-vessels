package com.example.skuld.io.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vessel")
public class VesselEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    @Column(name = "member", length = 50, nullable = false)
    private String member;

    @Column(name = "reg_owner", length = 50, nullable = false)
    private String regOwner;

    @Column(name = "flag", length = 50, nullable = false)
    private String flag;

    @Column(name = "gt", precision = 10, scale = 2, nullable = false)
    private int gt;

    @Column(name = "imo_no", unique = true, length = 7, nullable = false)
    private String imoNo;

    @Column(name = "year_built", columnDefinition = "YEAR(4)", nullable = false)
    private int yearBuilt;

    @Column(name = "vessel_type", nullable = false)
    private String vesselType;

    @Column(name = "business_units", nullable = false)
    private String businessUnits;

    @OneToMany(mappedBy = "vesselEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuildCertificateEntity> buildCertificateEntities;

    /**
     * We want to ensure that the buildCertificateEntities collection is initialized before adding elements to it.
     **/
    public VesselEntity() {
        buildCertificateEntities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getRegOwner() {
        return regOwner;
    }

    public void setRegOwner(String regOwner) {
        this.regOwner = regOwner;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getGt() {
        return gt;
    }

    public void setGt(int gt) {
        this.gt = gt;
    }

    public String getImoNo() {
        return imoNo;
    }

    public void setImoNo(String imoNo) {
        this.imoNo = imoNo;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }


    public String getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(String businessUnits) {
        this.businessUnits = businessUnits;
    }

    public List<BuildCertificateEntity> getBuildCertificateEntities() {
        return buildCertificateEntities;
    }

    public void setBuildCertificateEntities(List<BuildCertificateEntity> buildCertificateEntities) {
        this.buildCertificateEntities = buildCertificateEntities;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }
}