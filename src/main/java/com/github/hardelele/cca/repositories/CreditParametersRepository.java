package com.github.hardelele.cca.repositories;

import com.github.hardelele.cca.models.entities.CreditParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditParametersRepository extends JpaRepository<CreditParameters, UUID> {
}