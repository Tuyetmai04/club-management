package com.clubmanagement.service;

import com.clubmanagement.entity.Contribution;

import java.math.BigDecimal;
import java.util.List;

public interface ContributionService {

    List<Contribution> getAllContributions();

    List<Contribution> searchContributions(String keyword);

    Contribution getContributionById(Long id);

    Contribution saveContribution(Contribution contribution);

    Contribution updateContribution(Long id, Contribution contributionDetails);

    void deleteContribution(Long id);

    boolean existsByContributionCode(String contributionCode);

    boolean isContributionCodeDuplicate(String contributionCode, Long id);

    long countTotalContributions();

    BigDecimal getTotalContributionAmount();
}
