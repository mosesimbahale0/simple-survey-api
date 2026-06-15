package com.skyworld.skyform.repository;

import com.skyworld.skyform.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}