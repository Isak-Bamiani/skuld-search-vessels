package com.example.skuld.controller;

import com.example.skuld.exceptions.GlobalExceptionHandler.ErrorResponse;
import com.example.skuld.exceptions.VesselExceptions.DuplicateVesselException;
import com.example.skuld.model.request.VesselRequestModel;
import com.example.skuld.model.response.VesselSearchResponseModel;
import com.example.skuld.service.VesselService;
import com.example.skuld.shared.VesselDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@Validated
@RequestMapping("/vessels")
public class VesselController {
    @Autowired
    VesselService vesselService;

    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    // Exception handler for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid request: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Search vessels based on the provided parameters.
     *
     * @param searchTerm the search term
     * @param page       the page number
     * @param limit      the maximum number of results per page
     * @return a list of vesselSearchResponse models
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<VesselSearchResponseModel> searchVessels(@RequestParam(value = "q") String searchTerm, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "4") int limit) {

        validateSearchTerm(searchTerm);
        // Retrieve a list of VesselDto objects from the vesselService
        List<VesselDto> vesselDtos = vesselService.searchVessels(searchTerm, page, limit);

        // Use ModelMapper to map the list of VesselDto objects to a list of VesselSearchResponseModel objects
        Type listType = new TypeToken<List<VesselSearchResponseModel>>() {
        }.getType();
        return new ModelMapper().map(vesselDtos, listType);
    }

    private void validateSearchTerm(String searchTerm) {
        String pattern = "[^a-zA-Z0-9\\s]"; // Allow letters, digits, and spaces
        boolean containsSpecialSymbols = searchTerm.matches(".*" + pattern + ".*");

        // If the searchTerm contains special symbols, throw an IllegalArgumentException
        if (containsSpecialSymbols) {
            throw new IllegalArgumentException("Invalid search term: contains special symbols");
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> createVessel(@Valid @RequestBody VesselRequestModel vesselRequest) {
        try {
            // Map the VesselRequestModel to a VesselDto using ModelMapper
            VesselDto vesselDto = new ModelMapper().map(vesselRequest, VesselDto.class);

            // Call the vesselService to create a new vessel using the VesselDto
            VesselDto createdVessel = vesselService.createVessel(vesselDto);
            // If the vessel was created successfully, return a success response with the vessel's ID
            if (createdVessel != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Vessel created successfully with ID: "
                        + createdVessel.getImoNo());
            } else {
                // If there was an error creating the vessel, return an internal server error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create vessel");
            }
        } catch (DuplicateVesselException ex) {
            // If a DuplicateVesselException is thrown, return a conflict response with an error message
            ErrorResponse errorResponse = new ErrorResponse("Duplicate vessel: " + ex.getMessage(),
                    HttpStatus.CONFLICT.value());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse.getMessage());
        } catch (IllegalArgumentException ex) {
            // If an IllegalArgumentException is thrown, return a bad request response with an error message
            ErrorResponse errorResponse = new ErrorResponse("Invalid request: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse.getMessage());
        }
    }
}