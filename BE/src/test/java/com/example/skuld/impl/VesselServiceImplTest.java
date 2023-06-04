package com.example.skuld.impl;

import com.example.skuld.exceptions.VesselExceptions.DuplicateVesselException;
import com.example.skuld.exceptions.VesselExceptions.VesselNotFoundException;
import com.example.skuld.io.entity.VesselEntity;
import com.example.skuld.io.repository.BuildCertificateRepository;
import com.example.skuld.io.repository.VesselRepository;
import com.example.skuld.service.VesselService;
import com.example.skuld.service.impl.VesselServiceImpl;
import com.example.skuld.shared.BuildCertificateResponseDto;
import com.example.skuld.shared.VesselDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class VesselServiceImplTest {

    private VesselRepository vesselRepository;
    private BuildCertificateRepository buildCertificateRepository;
    private VesselService vesselService;

    @BeforeEach
    void setUp() {
        vesselRepository = mock(VesselRepository.class);
        buildCertificateRepository = mock(BuildCertificateRepository.class);
        vesselService = new VesselServiceImpl(vesselRepository);
    }

    @Test
    void searchVessels_withMatchingSearchTerm_shouldReturnVesselDtoList() {
        // Arrange
        String searchTerm = "ABC";
        int page = 1;
        int limit = 10;

        VesselEntity vesselEntity = new VesselEntity();
        vesselEntity.setImoNo("IMO123");
        vesselEntity.setName("ABC Vessel");

        List<VesselEntity> vesselEntities = new ArrayList<>();
        vesselEntities.add(vesselEntity);

        when(vesselRepository.findByNameContainingIgnoreCase(searchTerm)).thenReturn(vesselEntities);

        // Act
        List<VesselDto> result = vesselService.searchVessels(searchTerm, page, limit);

        // Assert
        assertEquals(1, result.size());
        assertEquals("IMO123", result.get(0).getImoNo());
        assertEquals("ABC Vessel", result.get(0).getName());

        verify(vesselRepository, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    void searchVessels_withNonMatchingSearchTerm_shouldThrowVesselNotFoundException() {
        // Arrange
        String searchTerm = "XYZ";
        int page = 1;
        int limit = 10;

        when(vesselRepository.findByNameContainingIgnoreCase(searchTerm)).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(VesselNotFoundException.class, () -> vesselService.searchVessels(searchTerm, page, limit));

        verify(vesselRepository, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }


    @Test
    void createVessel_withExistingVessel_shouldThrowDuplicateVesselException() {
        // Arrange
        String imoNo = "3333333";
        String vesselName = "Cargo Express";

        BuildCertificateResponseDto buildCertificateDto = new BuildCertificateResponseDto();
        buildCertificateDto.setType("Yachts Greek certificate");
        buildCertificateDto.setValidFrom(LocalDate.of(2023, 3, 27));
        buildCertificateDto.setValidTo(LocalDate.of(2024, 3, 26));

        List<BuildCertificateResponseDto> buildCertificateDtoList = new ArrayList<>();
        buildCertificateDtoList.add(buildCertificateDto);

        VesselDto vesselDto = new VesselDto();
        vesselDto.setImoNo(imoNo);
        vesselDto.setName(vesselName);
        vesselDto.setName(vesselName);
        vesselDto.setName(vesselName);
        vesselDto.setName(vesselName);

        vesselDto.setBuildCertificateDto(buildCertificateDtoList);

        VesselEntity existingVesselEntity = createVesselEntity(vesselName, imoNo, "Singapore",
                "containerShip", 55000, "Skuld Asia", "Cargo Shipping Company", "Cargo Shipping Company",
                2010);

        when(vesselRepository.findByImoNo(imoNo)).thenReturn(existingVesselEntity);

        // Act and Assert
        assertThrows(DuplicateVesselException.class, () -> vesselService.createVessel(vesselDto));

        verify(vesselRepository, times(1)).findByImoNo(imoNo);
        verify(vesselRepository, never()).save(any(VesselEntity.class));
    }

    private VesselEntity createVesselEntity(String name, String imoNo, String flag, String vesselType,
                                            int gt, String businessUnits, String regOwner, String member, int yearBuilt) {
        VesselEntity vesselEntity = new VesselEntity();
        vesselEntity.setName(name);
        vesselEntity.setImoNo(imoNo);
        vesselEntity.setFlag(flag);
        vesselEntity.setVesselType(vesselType);
        vesselEntity.setGt(gt);
        vesselEntity.setBusinessUnits(businessUnits);
        vesselEntity.setRegOwner(regOwner);
        vesselEntity.setMember(member);
        vesselEntity.setYearBuilt(yearBuilt);
        return vesselEntity;
    }

}
