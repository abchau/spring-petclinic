/*
 * Copyright 2012-2019 the original author or authors.
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
package org.springframework.samples.petclinic.vet.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.function.Function;

import org.springframework.samples.petclinic.vet.domain.Specialty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Models a {@link VetEntity Vet's} specialty (for example, dentistry).
 *
 * @author Juergen Hoeller
 * @author github.com/abchau
 */
@Entity
@Table(name = "specialties")
class SpecialtyEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	static Function<SpecialtyEntity, Specialty> translateToDomainModel = (specialtyEntity) -> {
		Specialty specialty = new Specialty();
		specialty.setId(specialtyEntity.getId());
		specialty.setName(specialtyEntity.getName());

		return specialty;
	};

	static Function<Specialty, SpecialtyEntity> translateToPersistenceModel = (specialty) -> {
		SpecialtyEntity specialtyEntity = new SpecialtyEntity();
		specialtyEntity.setId(specialty.getId());
		specialtyEntity.setName(specialty.getName());

		return specialtyEntity;
	};


}
