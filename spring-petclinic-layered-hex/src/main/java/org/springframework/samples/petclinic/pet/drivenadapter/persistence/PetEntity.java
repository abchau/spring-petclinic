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
package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.Visit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Simple jpa entity representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author github.com/abchau
 */
@Entity
@NamedEntityGraph(name = "PetEntity.visits", attributeNodes = @NamedAttributeNode("visits"))
@Table(name = "pets")
class PetEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@Column(name = "type_id")
	private Integer typeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id", updatable = false, insertable = false)
	private PetTypeEntity type;

	@Column(name = "owner_id")
	private Integer ownerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", updatable = false, insertable = false)
	private OwnerEntity owner;

	@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("visit_date ASC")
	private Set<VisitEntity> visits;

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

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public PetTypeEntity getType() {
		return this.type;
	}

	public void setType(PetTypeEntity type) {
		this.type = type;
	}

	public Integer getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public OwnerEntity getOwner() {
		return this.owner;
	}

	public void setOwner(OwnerEntity owner) {
		this.owner = owner;
	}

	public Set<VisitEntity> getVisits() {
		return visits;
	}

	public void setVisits(Set<VisitEntity> visits) {
		this.visits = visits;
	}

	@Override
	public String toString() {
		return "PetEntity [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", typeId=" + typeId + ", type="
				+ type + ", ownerId=" + ownerId + ", owner=" + owner + ", visits=" + visits + "]";
	}

	static Function<PetEntity, Pet> translateToDomainModel = (petEntity) -> {
		Pet pet = new Pet();
		pet.setId(petEntity.getId());
		pet.setOwnerId(petEntity.getOwnerId());
		pet.setName(petEntity.getName());
		pet.setBirthDate(petEntity.getBirthDate());
		if (petEntity.getType() != null) {
			pet.setType(PetTypeEntity.translateToDomainModel.apply(petEntity.getType()));
		}
		if (petEntity.getVisits() != null) {
			pet.setVisits(petEntity.getVisits().stream()
				.map(VisitEntity.translateToDomainModel)
				.collect(Collectors.toList()));
		}

		return pet;
	};

	static Function<Pet, PetEntity> translateToPersistenceModel = (pet) -> {
		PetEntity petEntity = new PetEntity();
		petEntity.setId(pet.getId());
		petEntity.setOwnerId(pet.getOwnerId());
		petEntity.setName(pet.getName());
		petEntity.setBirthDate(pet.getBirthDate());
		petEntity.setTypeId(pet.getType().getId());

		return petEntity;
	};

}
