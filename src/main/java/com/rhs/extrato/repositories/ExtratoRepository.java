package com.rhs.extrato.repositories;

import com.rhs.extrato.models.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, UUID> {
}
