package nkspring.splearn.application.member.member.provided;

import nkspring.splearn.domain.member.Member;

/**
 * 회원을 조회한다
 */
public interface MemberFinder {
    Member find(Long memberId);
}
