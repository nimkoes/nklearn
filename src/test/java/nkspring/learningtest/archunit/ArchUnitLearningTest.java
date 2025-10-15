package nkspring.learningtest.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * ArchUnit을 사용한 아키텍처 테스트
 *
 * @AnalyzeClasses: 분석할 패키지 지정
 * - packages: 분석할 패키지 경로 (..은 하위 패키지 포함)
 * - @ArchTest: 아키텍처 규칙을 정의하는 메서드
 */
@AnalyzeClasses(packages = "nkspring.learningtest.archunit")
public class ArchUnitLearningTest {

    /**
     * Application 레이어 의존성 규칙
     * 규칙: application 패키지의 클래스는 오직 application, adapter 패키지에서만 참조 가능
     * classes().that().resideInAPackage("..application..")
     * - 대상: application 패키지에 있는 모든 클래스
     * .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
     * - 조건: 이 클래스들을 의존하는 클래스는 application 또는 adapter 패키지에만 있어야 함
     * - 즉, domain이나 다른 레이어에서 application을 직접 참조하면 안됨
     */
    @ArchTest
    void application_should_only_be_accessed_by_application_and_adapter(JavaClasses classes) {
        classes().that().resideInAPackage("..application..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
                .check(classes);
    }

    /**
     * Application 레이어 역방향 의존성 규칙
     * 규칙: application 패키지는 adapter 패키지를 의존하면 안됨
     * noClasses().that().resideInAPackage("..application..")
     * - 대상: application 패키지의 모든 클래스
     * .should().dependOnClassesThat().resideInAPackage("..adapter..")
     * - 금지: adapter 패키지의 클래스를 의존하는 것
     * - 즉, application이 adapter를 직접 참조하면 안됨 (의존성 역전 원칙)
     */
    @ArchTest
    void application_should_not_depend_on_adapter(JavaClasses classes) {
        noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..adapter..")
                .check(classes);
    }

    /**
     * Domain 레이어 순수성 규칙
     * 규칙: domain 패키지는 오직 domain과 java 기본 클래스만 의존 가능
     * classes().that().resideInAPackage("..domain..")
     * - 대상: domain 패키지의 모든 클래스
     * .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "java..")
     * - 조건: domain 또는 java 기본 패키지만 의존 가능
     * - 즉, domain은 외부 라이브러리나 다른 레이어를 의존하면 안됨 (순수한 비즈니스 로직)
     */
    @ArchTest
    void domain_should_only_depend_on_domain_and_java(JavaClasses classes) {
        classes().that().resideInAPackage("..domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "java..")
                .check(classes);
    }
}
