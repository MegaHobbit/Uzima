package com.Uzima.repository;

import com.Uzima.models.ServicePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePointRepository extends JpaRepository<ServicePoint, Long> {
}
