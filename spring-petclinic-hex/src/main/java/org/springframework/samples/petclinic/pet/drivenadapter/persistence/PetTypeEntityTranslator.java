package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import org.springframework.samples.petclinic.pet.domain.PetType;

final class PetTypeEntityTranslator {

	static PetType toDomainModel(PetTypeEntity petTypeEntity) {
		PetType petType = new PetType();
		petType.setId(petTypeEntity.getId());
		petType.setName(petTypeEntity.getName());

		return petType;
	}

}
