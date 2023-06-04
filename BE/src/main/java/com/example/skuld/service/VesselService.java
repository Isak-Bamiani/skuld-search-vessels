package com.example.skuld.service;

import com.example.skuld.shared.VesselDto;

import java.util.List;

public interface VesselService {
    List<VesselDto> searchVessels(String searchTerm, int page, int limit);

    VesselDto createVessel(VesselDto vesselDto);

}

