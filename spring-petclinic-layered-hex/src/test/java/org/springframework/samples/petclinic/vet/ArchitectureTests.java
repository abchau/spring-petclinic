/*
 * Copyright 2023- the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * @author github.com/abchau
 */
@AnalyzeClasses(packages = "org.springframework.samples.petclinic.vet")
class ArchitectureTests {

    @ArchTest
	private static final ArchRule pet_layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()
		// Define layers
		.layer("Driving Adapter").definedBy("org.springframework.samples.petclinic.vet.drivingadapter..")
		.layer("Application").definedBy("org.springframework.samples.petclinic.vet.application..")
		.layer("Driving Port").definedBy("org.springframework.samples.petclinic.vet.application.drivingport..")
		.layer("Business Logic").definedBy("org.springframework.samples.petclinic.vet.application.businesslogic..")
		.layer("Driven Port").definedBy("org.springframework.samples.petclinic.vet.application.drivenport..")
		.layer("Driven Adapter").definedBy("org.springframework.samples.petclinic.vet.drivenadapter..")
		// Add constraints
		.whereLayer("Driving Adapter").mayNotBeAccessedByAnyLayer()
		.whereLayer("Application").mayOnlyBeAccessedByLayers("Driving Adapter", "Driven Adapter")
		.whereLayer("Driving Port").mayOnlyBeAccessedByLayers("Driving Adapter", "Business Logic")
		.whereLayer("Business Logic").mayOnlyBeAccessedByLayers("Driving Adapter")
		.whereLayer("Driven Port").mayOnlyBeAccessedByLayers("Business Logic", "Driven Adapter")
		.whereLayer("Driven Adapter").mayNotBeAccessedByAnyLayer();

}
