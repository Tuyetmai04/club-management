package com.clubmanagement.repository;

import com.clubmanagement.entity.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    Optional<Contribution> findByContributionCode(String contributionCode);

    boolean existsByContributionCode(String contributionCode);

    boolean existsByContributionCodeAndIdNot(String contributionCode, Long id);

    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM Contribution c")
    BigDecimal getTotalContributionAmount();

    @Query("SELECT c FROM Contribution c WHERE :keyword IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.contributionCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.collector) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.member.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY c.contributionDate DESC")
    List<Contribution> searchContributions(@Param("keyword") String keyword);
}
