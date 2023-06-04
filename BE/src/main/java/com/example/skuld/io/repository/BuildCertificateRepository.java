package com.example.skuld.io.repository;

import com.example.skuld.io.entity.BuildCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildCertificateRepository extends JpaRepository<BuildCertificateEntity, Long> {

    BuildCertificateEntity save(BuildCertificateEntity buildCertificateEntity);

}
