package nkspring.splearn.application;

import lombok.RequiredArgsConstructor;
import nkspring.splearn.application.provided.MemberRegister;
import nkspring.splearn.application.required.EmailSender;
import nkspring.splearn.application.required.MemberRepository;
import nkspring.splearn.domain.Member;
import nkspring.splearn.domain.MemberRegisterRequest;
import nkspring.splearn.domain.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");

        return member;
    }
}
