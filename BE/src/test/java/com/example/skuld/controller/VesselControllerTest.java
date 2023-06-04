package com.example.skuld.controller;

import com.example.skuld.model.response.VesselSearchResponseModel;
import com.example.skuld.service.VesselService;
import com.example.skuld.shared.VesselDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VesselControllerTest {

    @Mock
    private VesselService vesselService;

    @InjectMocks
    private VesselController vesselController;

    // @Mock annotated fields get initialized.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSearchVessels() {
        // Mock the vesselService's searchVessels method to return a list of VesselDto objects
        List<VesselDto> vesselDtos = Arrays.asList(new VesselDto(), new VesselDto());
        when(vesselService.searchVessels(anyString(), anyInt(), anyInt())).thenReturn(vesselDtos);

        // Call the searchVessels method in the VesselController
        List<VesselSearchResponseModel> responseModels = vesselController.searchVessels("searchTerm", 1, 2);

        // Verify that the vesselService's searchVessels method is called with the correct arguments
        verify(vesselService, times(1)).searchVessels(eq("searchTerm"), eq(1), eq(2));

        // Assert the size of the responseModels list is equal to the size of vesselDtos
        assertEquals(vesselDtos.size(), responseModels.size());
    }


}