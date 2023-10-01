package org.springframework.samples.petclinic.pet;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.springframework.samples.petclinic.pet")
class ArchitectureTests {

    @ArchTest
	private static final ArchRule pet_layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
		// Define layers
		.layer("Presentation").definedBy("org.springframework.samples.petclinic.pet.presentation..")
		.layer("Service").definedBy("org.springframework.samples.petclinic.pet.service..")
		.layer("Model").definedBy("org.springframework.samples.petclinic.pet.model..")
		.layer("Dao").definedBy("org.springframework.samples.petclinic.pet.dao..")
		// Add constraints
		.whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
		.whereLayer("Service").mayOnlyBeAccessedByLayers("Presentation")
		.whereLayer("Model").mayOnlyBeAccessedByLayers("Presentation", "Service", "Dao")
		.whereLayer("Dao").mayOnlyBeAccessedByLayers("Service");
	
}
