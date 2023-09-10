package org.springframework.samples.petclinic.vet.drivenadapter.persistence;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.vet.domain.Vet;

final class VetEntityTranslator {

	static Vet toDomainModel(VetEntity vetEntity) {
		Vet vet = new Vet();
		vet.setId(vetEntity.getId());
		vet.setFirstName(vetEntity.getFirstName());
		vet.setLastName(vetEntity.getLastName());
		vet.setSpecialties(vetEntity.getSpecialties()
			.stream()
			.map(SpecialtyEntityTranslator::toDomainModel)
			.collect(Collectors.toList()));

		return vet;
	}

	static VetEntity toPersistenceModel(Vet vet) {
		VetEntity vetEntity = new VetEntity();
		vetEntity.setId(vet.getId());
		vetEntity.setFirstName(vet.getFirstName());
		vetEntity.setLastName(vet.getLastName());
		vetEntity.setSpecialties(vet.getSpecialties()
			.stream()
			.map(SpecialtyEntityTranslator::toPersistenceModel)
			.collect(Collectors.toList()));

		return vetEntity;
	}

}