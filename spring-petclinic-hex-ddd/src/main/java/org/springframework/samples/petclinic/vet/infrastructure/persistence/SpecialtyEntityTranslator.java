package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import org.springframework.samples.petclinic.vet.domain.vet.Specialty;

class SpecialtyEntityTranslator {

	public static Specialty toDomainModel(SpecialtyEntity specialtyEntity) {
		Specialty specialty = new Specialty();
		specialty.setId(specialtyEntity.getId());
		specialty.setName(specialtyEntity.getName());

		return specialty;
	}

	public static SpecialtyEntity toPersistenceModel(Specialty specialty) {
		SpecialtyEntity specialtyEntity = new SpecialtyEntity();
		specialtyEntity.setId(specialty.getId());
		specialtyEntity.setName(specialty.getName());

		return specialtyEntity;
	}

}
