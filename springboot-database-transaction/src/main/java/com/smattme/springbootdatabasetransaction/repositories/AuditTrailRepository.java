package com.smattme.springbootdatabasetransaction.repositories;

import com.smattme.springbootdatabasetransaction.models.AuditTrail;
import org.springframework.data.repository.CrudRepository;

public interface AuditTrailRepository extends CrudRepository<AuditTrail, Long> {
}
