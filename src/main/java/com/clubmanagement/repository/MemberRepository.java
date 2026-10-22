package com.clubmanagement.repository;

import com.clubmanagement.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberCode(String memberCode);

    boolean existsByMemberCode(String memberCode);

    boolean existsByMemberCodeAndIdNot(String memberCode, Long id);

    List<Member> findByFullNameContainingIgnoreCaseOrMemberCodeContainingIgnoreCase(String keywordName, String keywordCode);

    long countByStatus(String status);

    @Query("SELECT m FROM Member m WHERE (:keyword IS NULL OR LOWER(m.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.memberCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND (:status IS NULL OR :status = '' OR m.status = :status) ORDER BY m.id DESC")
    List<Member> searchMembers(@Param("keyword") String keyword, @Param("status") String status);
}
