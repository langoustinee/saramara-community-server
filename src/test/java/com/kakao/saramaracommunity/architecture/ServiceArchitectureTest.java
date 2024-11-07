package com.kakao.saramaracommunity.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

import static com.kakao.saramaracommunity.architecture.support.CommonPackageProvider.combineWithCommonPackages;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(
        packages = "com.kakao.saramaracommunity.bucket",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ServiceArchitectureTest {

    @ArchTest
    static final ArchRule service_패키지의_의존성_검사를_검증한다 =
            classes()
                    .that().resideInAPackage("..service..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(combineWithCommonPackages("..service.port.."));

    @ArchTest
    static final ArchRule service_패키지_내_구현_클래스_이름은_ServiceImpl로_끝나야_한다 =
            classes()
                    .that().resideInAPackage("..service..")
                    .and().resideOutsideOfPackage("..service.port..")
                    .should().haveSimpleNameEndingWith("ServiceImpl");

    @ArchTest
    static final ArchRule service_port_패키지의_서비스는_추상화된_Interface_타입이어야_한다 =
            classes()
                    .that().resideInAnyPackage("..service.port..")
                    .should().beInterfaces();

    @ArchTest
    static final ArchRule Service_클래스는_controller_port_패키지의_Service_인터페이스를_구현해야_한다 =
            classes()
                    .that().resideInAPackage("..service..")
                    .and().areNotInterfaces()
                    .should().implement(resideInAPackage("..controller.port.."));

    @ArchTest
    static final ArchRule service_패키지의_구현_클래스는_반드시_Service_어노테이션이_선언되어야_한다 =
            classes()
                    .that().resideInAPackage("..service..")
                    .and().resideOutsideOfPackage("..service.port..")
                    .should().beAnnotatedWith(Service.class);

}
