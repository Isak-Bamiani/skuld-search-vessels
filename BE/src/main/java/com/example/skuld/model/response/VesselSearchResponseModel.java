package com.example.skuld.model.response;

import com.example.skuld.shared.BuildCertificateResponseDto;

import java.util.List;

public class VesselSearchResponseModel {
    private String name;
    private String imoNo;
    private String flag;
    private int gt;
    private int yearBuilt;
    private String member;
    private String regOwner;
    private List<BuildCertificateResponseDto> BuildCertificateResponseDto;
    private String vesselType;
    private String businessUnits;

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


    public List<BuildCertificateResponseDto> getBuildCertificateDto() {
        return BuildCertificateResponseDto;
    }

    public void setBuildCertificateDto(List<BuildCertificateResponseDto> buildCertificateResponseDto) {
        BuildCertificateResponseDto = buildCertificateResponseDto;
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

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public String getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(String businessUnits) {
        this.businessUnits = businessUnits;
    }
}
