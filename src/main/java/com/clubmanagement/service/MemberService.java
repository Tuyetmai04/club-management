package com.clubmanagement.service;

import com.clubmanagement.entity.Member;

import java.util.List;

public interface MemberService {

    List<Member> getAllMembers();

    List<Member> searchMembers(String keyword, String status);

    Member getMemberById(Long id);

    Member saveMember(Member member);

    Member updateMember(Long id, Member memberDetails);

    void deleteMember(Long id);

    boolean existsByMemberCode(String memberCode);

    boolean isMemberCodeDuplicate(String memberCode, Long id);

    long countTotalMembers();

    long countActiveMembers();
}
