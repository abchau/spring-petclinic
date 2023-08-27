package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import org.springframework.samples.petclinic.pet.domain.pet.PetType;

public class PetTypeEntityTranslator {

	public static PetType toDomainModel(PetTypeEntity petTypeEntity) {
		PetType petType = new PetType();
		petType.setId(petTypeEntity.getId());
		petType.setName(petTypeEntity.getName());

		return petType;
	}

}
