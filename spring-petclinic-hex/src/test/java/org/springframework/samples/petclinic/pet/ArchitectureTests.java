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
		.layer("Driving Adapter").definedBy("org.springframework.samples.petclinic.pet.drivingadapter..")
		.layer("Application").definedBy("org.springframework.samples.petclinic.pet.application..")
		.layer("Driving Port").definedBy("org.springframework.samples.petclinic.pet.application.drivingport..")
		.layer("Business Logic").definedBy("org.springframework.samples.petclinic.pet.application.businesslogic..")
		.layer("Driven Port").definedBy("org.springframework.samples.petclinic.pet.application.drivenport..")
		.layer("Driven Adapter").definedBy("org.springframework.samples.petclinic.pet.drivenadapter..")
		// Add constraints
		.whereLayer("Driving Adapter").mayNotBeAccessedByAnyLayer()
		.whereLayer("Application").mayOnlyBeAccessedByLayers("Driving Adapter", "Driven Adapter")
		.whereLayer("Driving Port").mayOnlyBeAccessedByLayers("Driving Adapter", "Business Logic")
		.whereLayer("Business Logic").mayOnlyBeAccessedByLayers("Driving Adapter")
		.whereLayer("Driven Port").mayOnlyBeAccessedByLayers("Business Logic", "Driven Adapter")
		.whereLayer("Driven Adapter").mayNotBeAccessedByAnyLayer();

}
