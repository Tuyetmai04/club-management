package com.clubmanagement.service.impl;

import com.clubmanagement.entity.Member;
import com.clubmanagement.repository.MemberRepository;
import com.clubmanagement.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> searchMembers(String keyword, String status) {
        return memberRepository.searchMembers(keyword, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên với ID: " + id));
    }

    @Override
    public Member saveMember(Member member) {
        if (member.getId() == null && existsByMemberCode(member.getMemberCode())) {
            throw new IllegalArgumentException("Mã thành viên '" + member.getMemberCode() + "' đã tồn tại!");
        }
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long id, Member memberDetails) {
        Member existingMember = getMemberById(id);
        if (isMemberCodeDuplicate(memberDetails.getMemberCode(), id)) {
            throw new IllegalArgumentException("Mã thành viên '" + memberDetails.getMemberCode() + "' đã thuộc về thành viên khác!");
        }
        existingMember.setMemberCode(memberDetails.getMemberCode());
        existingMember.setFullName(memberDetails.getFullName());
        existingMember.setDateOfBirth(memberDetails.getDateOfBirth());
        existingMember.setEmail(memberDetails.getEmail());
        existingMember.setPhone(memberDetails.getPhone());
        existingMember.setJoinDate(memberDetails.getJoinDate());
        existingMember.setPosition(memberDetails.getPosition());
        existingMember.setStatus(memberDetails.getStatus());
        return memberRepository.save(existingMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMemberCode(String memberCode) {
        return memberRepository.existsByMemberCode(memberCode);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMemberCodeDuplicate(String memberCode, Long id) {
        if (id == null) {
            return memberRepository.existsByMemberCode(memberCode);
        }
        return memberRepository.existsByMemberCodeAndIdNot(memberCode, id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalMembers() {
        return memberRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveMembers() {
        return memberRepository.countByStatus("Đang hoạt động");
    }
}
