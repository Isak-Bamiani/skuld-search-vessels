package com.example.skuld.service.impl;

import com.example.skuld.exceptions.VesselExceptions.DuplicateVesselException;
import com.example.skuld.exceptions.VesselExceptions.VesselNotFoundException;
import com.example.skuld.io.entity.BuildCertificateEntity;
import com.example.skuld.io.entity.VesselEntity;
import com.example.skuld.io.repository.VesselRepository;
import com.example.skuld.service.VesselService;
import com.example.skuld.shared.BuildCertificateResponseDto;
import com.example.skuld.shared.VesselDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VesselServiceImpl implements VesselService {

    private final VesselRepository vesselRepository;

    @Autowired
    public VesselServiceImpl(VesselRepository vesselRepository) {
        this.vesselRepository = vesselRepository;
    }

    @Override
    public List<VesselDto> searchVessels(String searchTerm, int page, int limit) {
        List<VesselDto> vessels = new ArrayList<>();

        searchTerm = searchTerm.trim(); // Remove leading and trailing spaces

        if (searchTerm.matches("[0-9]+")) {
            // Search by IMO number
            VesselEntity vessel = vesselRepository.findByImoNo(searchTerm);
            if (vessel != null) {
                vessels.add(mapVesselEntityToDto(vessel));
            }
        } else if (searchTerm.matches("[a-zA-Z\s]+")) {
            // Search by vessel name (case-insensitive) including spaces
            List<VesselEntity> vesselEntities = vesselRepository.findByNameContainingIgnoreCase(searchTerm);
            for (VesselEntity vesselEntity : vesselEntities) {
                vessels.add(mapVesselEntityToDto(vesselEntity));
            }
        }
        if (vessels.isEmpty()) {
            throw new VesselNotFoundException("No vessels found with the given search term: " + searchTerm);
        }
        return vessels;
    }

    private VesselDto mapVesselEntityToDto(VesselEntity vesselEntity) {
        ModelMapper modelMapper = new ModelMapper();
        VesselDto vesselDto = modelMapper.map(vesselEntity, VesselDto.class);

        List<BuildCertificateResponseDto> buildCertificateResponseDtos = new ArrayList<>();
        for (BuildCertificateEntity buildCertificateEntity : vesselEntity.getBuildCertificateEntities()) {
            BuildCertificateResponseDto buildCertificateResponseDto = modelMapper.map(buildCertificateEntity, BuildCertificateResponseDto.class);
            buildCertificateResponseDtos.add(buildCertificateResponseDto);
        }

        vesselDto.setBuildCertificateDto(buildCertificateResponseDtos);
        return vesselDto;
    }

    @Override
    public VesselDto createVessel(VesselDto vesselDto) {
        ModelMapper modelMapper = new ModelMapper();

        VesselEntity existingVessel = vesselRepository.findByImoNo(vesselDto.getImoNo());

        if (existingVessel != null) {
            throw new DuplicateVesselException("Vessel with the same IMO already exists");
        }

        try {
            VesselEntity vesselEntity = modelMapper.map(vesselDto, VesselEntity.class);

            for (BuildCertificateResponseDto buildCertificateResponseDto : vesselDto.getBuildCertificateDto()) {
                BuildCertificateEntity buildCertificateEntity = modelMapper.map(buildCertificateResponseDto, BuildCertificateEntity.class);
                buildCertificateEntity.setVessel(vesselEntity); // Associate the build certificate with the vessel
                vesselEntity.getBuildCertificateEntities().add(buildCertificateEntity); // Add the build certificate to the vessel's collection
            }

            VesselEntity savedVesselEntity = vesselRepository.save(vesselEntity);

            return modelMapper.map(savedVesselEntity, VesselDto.class);
        } catch (DataAccessException ex) {
            // Error handling for data persistence failure
            throw new RuntimeException("Failed to create the vessel during the save operation. Please try again later.", ex);
        }
    }
}
