package com.nman.apiagent.repository;

import com.nman.apiagent.entity.Vpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VpaRepository
		extends JpaRepository<Vpa, Long> {

	Optional<Vpa> findByVpa(String vpa);

	boolean existsByVpa(String vpa);

	List<Vpa> findByAccountId(String accountId);

	Optional<Vpa> findByAccountIdAndPrimaryVpa(String accountId, boolean primaryVpa);
}
