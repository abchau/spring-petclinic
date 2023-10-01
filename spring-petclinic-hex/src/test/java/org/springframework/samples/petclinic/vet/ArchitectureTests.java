package org.springframework.samples.petclinic.vet;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.springframework.samples.petclinic.vet")
class ArchitectureTests {

    @ArchTest
	private static final ArchRule vet_layer_dependencies_are_respected = onionArchitecture()
			.domainModels("..domain..")
			.domainServices("..domain..")
			.applicationServices("..application..")
			.adapter("userinterfaxe", "..userinterfaxe..")
			.adapter("infrastructure", "..infrastructure..")
	
	// @ArchTest
	// private static final ArchRule vet_shoult_not_have_circular_dependencies = slices()
	// 	.matching("org.springframework.samples.petclinic.(*)..")
	// 	.should().beFreeOfCycles();
	

}
