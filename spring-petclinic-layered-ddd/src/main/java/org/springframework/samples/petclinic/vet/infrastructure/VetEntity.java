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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.vet.domain.Vet;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 * @author github.com/abchau
 */
@Entity
@Table(name = "vets")
class VetEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	@NotEmpty
	private String firstName;

	@Column(name = "last_name")
	@NotEmpty
	private String lastName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private List<SpecialtyEntity> specialties;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<SpecialtyEntity> getSpecialties() {
		return this.specialties;
	}

	public void setSpecialties(List<SpecialtyEntity> specialties) {
		this.specialties = specialties;
	}

	static Function<VetEntity, Vet> translateToDomainModel = (vetEntity) -> {
		Vet vet = new Vet();
		vet.setId(vetEntity.getId());
		vet.setFirstName(vetEntity.getFirstName());
		vet.setLastName(vetEntity.getLastName());
		vet.setSpecialties(vetEntity.getSpecialties().stream()
			.map(SpecialtyEntity.translateToDomainModel)
			.collect(Collectors.toList()));

		return vet;
	};

	static Function<Vet, VetEntity> translateToPersistenceModel = (vet) -> {
		VetEntity vetEntity = new VetEntity();
		vetEntity.setId(vet.getId());
		vetEntity.setFirstName(vet.getFirstName());
		vetEntity.setLastName(vet.getLastName());
		vetEntity.setSpecialties(vet.getSpecialties().stream()
			.map(SpecialtyEntity.translateToPersistenceModel)
			.collect(Collectors.toList()));

		return vetEntity;
	};

}
