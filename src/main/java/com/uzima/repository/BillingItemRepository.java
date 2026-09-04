package com.uzima.repository;

import com.uzima.models.BillingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingItemRepository extends JpaRepository<BillingItem, Long> {

    List<BillingItem> findByBillingIdAndDeletedFlagFalse(Long billingId);
}
