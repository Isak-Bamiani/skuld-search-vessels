package com.example.skuld.io.repository;


import com.example.skuld.io.entity.VesselEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VesselRepository extends JpaRepository<VesselEntity, Long> {
    List<VesselEntity> findByNameContainingIgnoreCase(String name);

    VesselEntity findByImoNo(String imoNo);

    void deleteAll();

    long count();

    Optional<VesselEntity> findById(Long id);

    void delete(VesselEntity vesselEntity);
}