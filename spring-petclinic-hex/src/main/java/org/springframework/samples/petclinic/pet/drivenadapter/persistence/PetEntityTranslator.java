package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.domain.Pet;

public class PetEntityTranslator {

	public static Pet toDomainModel(PetEntity petEntity) {
		Pet pet = new Pet();
		pet.setId(petEntity.getId());
		pet.setOwnerId(petEntity.getOwnerId());
		pet.setName(petEntity.getName());
		pet.setBirthDate(petEntity.getBirthDate());
		if (petEntity.getType() != null) {
			pet.setType(PetTypeEntityTranslator.toDomainModel(petEntity.getType()));
		}
		if (petEntity.getVisits() != null) {
			pet.setVisits(petEntity.getVisits()
				.stream()
				.map(VisitEntityTranslator::toDomainModel)
				.collect(Collectors.toList()));
		}

		return pet;
	}

	public static PetEntity toPersistenceModel(Pet pet) {
		PetEntity petEntity = new PetEntity();
		petEntity.setId(pet.getId());
		petEntity.setOwnerId(pet.getOwnerId());
		petEntity.setName(pet.getName());
		petEntity.setBirthDate(pet.getBirthDate());
		petEntity.setTypeId(pet.getType().getId());

		return petEntity;
	}

}
