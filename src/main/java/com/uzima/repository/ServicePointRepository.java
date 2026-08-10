package com.uzima.repository;

import com.uzima.models.ServicePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicePointRepository extends JpaRepository<ServicePoint, Long> {

    List<ServicePoint> findAllByDeletedFlag(Boolean deletedFlag);
}
