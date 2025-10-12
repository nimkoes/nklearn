package nkspring.splearn.application.member.member;

import lombok.RequiredArgsConstructor;
import nkspring.splearn.application.member.member.provided.MemberFinder;
import nkspring.splearn.application.member.member.required.MemberRepository;
import nkspring.splearn.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {

    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id : " + memberId));
    }
}
