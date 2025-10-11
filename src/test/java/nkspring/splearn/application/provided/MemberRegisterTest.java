package nkspring.splearn.application.provided;

import jakarta.validation.ConstraintViolationException;
import nkspring.splearn.SplearnTestConfiguration;
import nkspring.splearn.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public record MemberRegisterTest(MemberRegister memberRegister) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> {
            memberRegister.register(MemberFixture.createMemberRegisterRequest());
        }).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("nk@splearn.app", "nk", "longsecret"));
        extracted(new MemberRegisterRequest("nk@splearn.app", "Charlie____________________________", "longsecret"));
        extracted(new MemberRegisterRequest("nksplearn.app", "Charlie", "longsecret"));
    }

    private void extracted(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
