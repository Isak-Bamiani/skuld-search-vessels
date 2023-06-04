package com.example.skuld;


import com.example.skuld.io.entity.BuildCertificateEntity;
import com.example.skuld.io.entity.VesselEntity;
import com.example.skuld.io.repository.BuildCertificateRepository;
import com.example.skuld.io.repository.VesselRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class InitialVesselsSetup {
    private static final Logger logger = LoggerFactory.getLogger(InitialVesselsSetup.class);

    @Autowired
    private VesselRepository vesselRepository;

    @Autowired
    private BuildCertificateRepository buildCertificateRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            logger.info("**************** Initializing Vessels ****************");

            deleteAllData();

            // Check if data already exists
            if (dataAlreadyExists()) {
                logger.info("Data already initialized. Skipping initialization");
                return;
            }

            // Step 2: Create Vessel entities
            VesselEntity vesselEntity1 = createVesselEntity("Oceanic Star", "1234567", "United States", "cargo",
                    50000, "Business Unit 1", "John Doe", "Jane Smith", 2000);
            vesselRepository.save(vesselEntity1);

            VesselEntity vesselEntity5 = createVesselEntity("Oceanic", "1234867", "United States", "cargo",
                    50000, "Business Unit 1", "John Doe", "Jane Smith", 2000);
            vesselRepository.save(vesselEntity5);

            VesselEntity vesselEntity2 = createVesselEntity("Maritime Queen", "9876543", "United Kingdom",
                    "tanker", 150000, "Business Unit 2", "David Johnson", "Emily Brown", 1998);
            vesselRepository.save(vesselEntity2);

            VesselEntity vesselEntity3 = createVesselEntity("Cargo Express", "3333333", "Singapore",
                    "containerShip", 55000, "Skuld Asia", "Cargo Shipping Company", "Cargo Shipping Company",
                    2010);
            vesselRepository.save(vesselEntity3);

            VesselEntity vesselEntity4 = createVesselEntity("Tug Master", "2222222", "United Kingdom",
                    "tugboat", 120, "Skuld Western Europe and Americas", "Tug Solutions Ltd.",
                    "Tug Solutions Ltd.", 2015);
            vesselRepository.save(vesselEntity4);

            // Step 2: Create BuildCertificate entities
            BuildCertificateEntity certificate1 = createBuildCertificateEntity(vesselEntity1, "Type 1", LocalDate.now(),
                    LocalDate.now().plusYears(1));
            buildCertificateRepository.save(certificate1);

            BuildCertificateEntity certificate2 = createBuildCertificateEntity(vesselEntity1, "Type 1", LocalDate.now(),
                    LocalDate.now().plusYears(1));
            buildCertificateRepository.save(certificate2);

            BuildCertificateEntity certificate3 = createBuildCertificateEntity(vesselEntity2, "Yachts Greek certificate",
                    LocalDate.of(2023, 3, 27), LocalDate.of(2024, 3, 26));
            buildCertificateRepository.save(certificate3);

            BuildCertificateEntity certificate4 = createBuildCertificateEntity(vesselEntity3, "Yachts Greek certificate",
                    LocalDate.of(2023, 3, 27), LocalDate.of(2024, 3, 26));
            buildCertificateRepository.save(certificate4);

            BuildCertificateEntity certificate5 = createBuildCertificateEntity(vesselEntity4, "New Certificate",
                    LocalDate.of(2023, 5, 28), LocalDate.of(2024, 5, 27));
            buildCertificateRepository.save(certificate5);

            BuildCertificateEntity certificate6 = createBuildCertificateEntity(vesselEntity5, "Tugboat Certificate",
                    LocalDate.of(2023, 5, 1), LocalDate.of(2024, 4, 30));
            buildCertificateRepository.save(certificate6);

            logger.info("**************** Vessels Initialization Complete ****************");
        } catch (Exception e) {
            logger.error("Exception occurred during vessel initialization: {}", e.getMessage());

        }
    }

    private boolean dataAlreadyExists() {
        return vesselRepository.count() > 0 || buildCertificateRepository.count() > 0;
    }

    @Transactional
    public void deleteAllData() {
        try {
            logger.info("**************** Deleting All Vessels Data ****************");
            vesselRepository.deleteAll();
            buildCertificateRepository.deleteAll();
            logger.info("**************** All Vessels Data Deleted ****************");
        } catch (Exception e) {
            logger.error("Exception occurred during data deletion: {}", e.getMessage());

        }
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

    private BuildCertificateEntity createBuildCertificateEntity(VesselEntity vessel, String type, LocalDate validFrom,
                                                                LocalDate validTo) {
        BuildCertificateEntity certificateEntity = new BuildCertificateEntity();
        certificateEntity.setVessel(vessel);
        certificateEntity.setType(type);
        certificateEntity.setValidFrom(validFrom);
        certificateEntity.setValidTo(validTo);
        return certificateEntity;
    }
}
