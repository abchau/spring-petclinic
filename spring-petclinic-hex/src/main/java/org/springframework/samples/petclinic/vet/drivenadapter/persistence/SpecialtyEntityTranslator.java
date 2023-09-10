package org.springframework.samples.petclinic.vet.drivenadapter.persistence;

import org.springframework.samples.petclinic.vet.domain.Specialty;

final class SpecialtyEntityTranslator {

	static Specialty toDomainModel(SpecialtyEntity specialtyEntity) {
		Specialty specialty = new Specialty();
		specialty.setId(specialtyEntity.getId());
		specialty.setName(specialtyEntity.getName());

		return specialty;
	}

	static SpecialtyEntity toPersistenceModel(Specialty specialty) {
		SpecialtyEntity specialtyEntity = new SpecialtyEntity();
		specialtyEntity.setId(specialty.getId());
		specialtyEntity.setName(specialty.getName());

		return specialtyEntity;
	}

}
