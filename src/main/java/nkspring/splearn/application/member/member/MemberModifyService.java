package nkspring.splearn.application.member.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nkspring.splearn.application.member.member.provided.MemberFinder;
import nkspring.splearn.application.member.member.provided.MemberRegister;
import nkspring.splearn.application.member.member.required.EmailSender;
import nkspring.splearn.application.member.member.required.MemberRepository;
import nkspring.splearn.domain.member.DuplicateEmailException;
import nkspring.splearn.domain.member.Member;
import nkspring.splearn.domain.member.MemberRegisterRequest;
import nkspring.splearn.domain.member.PasswordEncoder;
import nkspring.splearn.domain.shared.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(@Valid MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일 입니다 : " + registerRequest.email());
        }
    }
}
