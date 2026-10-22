package com.clubmanagement.service.impl;

import com.clubmanagement.entity.Contribution;
import com.clubmanagement.repository.ContributionRepository;
import com.clubmanagement.service.ContributionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;

    public ContributionServiceImpl(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contribution> getAllContributions() {
        return contributionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contribution> searchContributions(String keyword) {
        return contributionRepository.searchContributions(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Contribution getContributionById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoản đóng góp với ID: " + id));
    }

    @Override
    public Contribution saveContribution(Contribution contribution) {
        if (contribution.getId() == null && existsByContributionCode(contribution.getContributionCode())) {
            throw new IllegalArgumentException("Mã đóng góp '" + contribution.getContributionCode() + "' đã tồn tại!");
        }
        return contributionRepository.save(contribution);
    }

    @Override
    public Contribution updateContribution(Long id, Contribution contributionDetails) {
        Contribution existing = getContributionById(id);
        if (isContributionCodeDuplicate(contributionDetails.getContributionCode(), id)) {
            throw new IllegalArgumentException("Mã đóng góp '" + contributionDetails.getContributionCode() + "' đã thuộc về khoản đóng góp khác!");
        }
        existing.setContributionCode(contributionDetails.getContributionCode());
        existing.setMember(contributionDetails.getMember());
        existing.setTitle(contributionDetails.getTitle());
        existing.setAmount(contributionDetails.getAmount());
        existing.setContributionDate(contributionDetails.getContributionDate());
        existing.setCollector(contributionDetails.getCollector());
        existing.setNote(contributionDetails.getNote());
        return contributionRepository.save(existing);
    }

    @Override
    public void deleteContribution(Long id) {
        Contribution contribution = getContributionById(id);
        contributionRepository.delete(contribution);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByContributionCode(String contributionCode) {
        return contributionRepository.existsByContributionCode(contributionCode);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isContributionCodeDuplicate(String contributionCode, Long id) {
        if (id == null) {
            return contributionRepository.existsByContributionCode(contributionCode);
        }
        return contributionRepository.existsByContributionCodeAndIdNot(contributionCode, id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalContributions() {
        return contributionRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalContributionAmount() {
        BigDecimal total = contributionRepository.getTotalContributionAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
}
