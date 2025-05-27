package com.maximase.document_converter_service.repository;

import com.maximase.document_converter_service.entity.ConversionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionLogRepository extends JpaRepository<ConversionLog, Long> {
}
