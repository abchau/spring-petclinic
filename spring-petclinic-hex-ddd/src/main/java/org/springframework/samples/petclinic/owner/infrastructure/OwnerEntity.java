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
package org.springframework.samples.petclinic.owner.infrastructure;

import java.util.List;
import java.util.Set;

import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.pet.infrastructure.PetEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

/**
 * Simple jpa entity representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Oliver Drotbohm
 * @author github.com/abchau
 */
@Entity
@NamedEntityGraph(name = "OwnerEntity.pets", attributeNodes = @NamedAttributeNode("pets"))
@Table(name = "owners")
public class OwnerEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "telephone")
	private String telephone;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("name")
	private Set<PetEntity> pets;

	// public boolean isNew() {
	// return this.id == null;
	// }

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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<PetEntity> getPets() {
		return this.pets;
	}

	public void setPets(Set<PetEntity> pets) {
		this.pets = pets;
	}

	// public void addPet(Pet pet) {
	// if (pet.isNew()) {
	// getPets().add(pet);
	// }
	// }

	// /**
	// * Return the Pet with the given name, or null if none found for this Owner.
	// * @param name to test
	// * @return a pet if pet name is already in use
	// */
	// public Pet getPet(String name) {
	// return getPet(name, false);
	// }

	// /**
	// * Return the Pet with the given id, or null if none found for this Owner.
	// * @param name to test
	// * @return a pet if pet id is already in use
	// */
	// public Pet getPet(Integer id) {
	// for (Pet pet : getPets()) {
	// if (!pet.isNew()) {
	// Integer compId = pet.getId();
	// if (compId.equals(id)) {
	// return pet;
	// }
	// }
	// }
	// return null;
	// }

	// /**
	// * Return the Pet with the given name, or null if none found for this Owner.
	// * @param name to test
	// * @return a pet if pet name is already in use
	// */
	// public Pet getPet(String name, boolean ignoreNew) {
	// name = name.toLowerCase();
	// for (Pet pet : getPets()) {
	// if (!ignoreNew || !pet.isNew()) {
	// String compName = pet.getName();
	// compName = compName == null ? "" : compName.toLowerCase();
	// if (compName.equals(name)) {
	// return pet;
	// }
	// }
	// }
	// return null;
	// }

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			// .append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("address", this.address)
			.append("city", this.city)
			.append("telephone", this.telephone)
			.toString();
	}

	// /**
	// * Adds the given {@link Visit} to the {@link Pet} with the given identifier.
	// * @param petId the identifier of the {@link Pet}, must not be {@literal null}.
	// * @param visit the visit to add, must not be {@literal null}.
	// */
	// public Owner addVisit(Integer petId, Visit visit) {

	// Assert.notNull(petId, "Pet identifier must not be null!");
	// Assert.notNull(visit, "Visit must not be null!");

	// Pet pet = getPet(petId);

	// Assert.notNull(pet, "Invalid Pet identifier!");

	// pet.addVisit(visit);

	// return this;
	// }

}
