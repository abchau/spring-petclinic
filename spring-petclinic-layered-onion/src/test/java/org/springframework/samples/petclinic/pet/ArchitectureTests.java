package org.springframework.samples.petclinic.pet;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.springframework.samples.petclinic.pet")
class ArchitectureTests {

	private static final ArchRule pet_layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
		// Define layers
		.layer("User Interface").definedBy("org.springframework.samples.petclinic.pet.userinterface..")
		.layer("Application Service").definedBy("org.springframework.samples.petclinic.pet.application..")
		.layer("Domain Model").definedBy("org.springframework.samples.petclinic.pet.domain..")
		.layer("Infrastructure").definedBy("org.springframework.samples.petclinic.pet.infrastructure..")
		// Add constraints
		.whereLayer("User Interface").mayNotBeAccessedByAnyLayer()
		.whereLayer("Application Service").mayOnlyBeAccessedByLayers("User Interface")
		.whereLayer("Domain Model").mayOnlyBeAccessedByLayers("User Interface", "Application Service", "Infrastructure")
		.whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Domain Model");
		
}
