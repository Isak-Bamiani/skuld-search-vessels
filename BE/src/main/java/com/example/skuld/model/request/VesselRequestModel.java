package com.example.skuld.model.request;

import com.example.skuld.shared.BuildCertificateResponseDto;
import jakarta.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

public class VesselRequestModel {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "IMO  is required")
    private String imoNo;
    @NotBlank(message = "Flag is required")
    private String flag;
    @NotBlank(message = "Vessel Type is required")
    private String vesselType;
    @NotNull(message = "Gross Tonnage is required")
    private Integer gt;
    @NotNull(message = "Year Built is required")
    private Integer yearBuilt;
    @NotBlank(message = "Member is required")
    private String member;
    @NotBlank(message = "Reg owner is required")
    private String regOwner;
    @NotBlank(message = "Business units is required")
    private String businessUnits;
    @NotNull(message = "Building certificate is required")
    private List<BuildCertificateResponseDto> BuildCertificateResponseDto;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImoNo() {
        return imoNo;
    }

    public void setImoNo(String imoNo) {
        this.imoNo = imoNo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public int getGt() {
        return gt;
    }

    public void setGt(int gt) {
        this.gt = gt;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
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

    public String getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(String businessUnits) {
        this.businessUnits = businessUnits;
    }

    public List<BuildCertificateResponseDto> getBuildCertificateDto() {
        return BuildCertificateResponseDto;
    }

    public void setBuildCertificateDto(List<BuildCertificateResponseDto> buildCertificateResponseDto) {
        BuildCertificateResponseDto = buildCertificateResponseDto;
    }
}
