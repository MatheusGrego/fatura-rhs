package com.rhs.extrato.repositories;

import com.rhs.extrato.models.FaturaCrlv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FaturaCrlvRepository extends JpaRepository<FaturaCrlv, UUID> {
}
