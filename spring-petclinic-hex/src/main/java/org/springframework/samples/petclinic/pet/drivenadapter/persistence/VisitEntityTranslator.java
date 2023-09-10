package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import org.springframework.samples.petclinic.pet.domain.Visit;

final class VisitEntityTranslator {

	static Visit toDomainModel(VisitEntity visitEntity) {
		Visit visit = new Visit();
		visit.setId(visitEntity.getId());
		visit.setPetId(visitEntity.getPetId());
		visit.setDate(visitEntity.getDate());
		visit.setDescription(visitEntity.getDescription());

		return visit;
	}

	static VisitEntity toPersistenceModel(Visit visit) {
		VisitEntity visitEntity = new VisitEntity();
		visitEntity.setId(visit.getId());
		visitEntity.setPetId(visit.getPetId());
		visitEntity.setDate(visit.getDate());
		visitEntity.setDescription(visit.getDescription());

		return visitEntity;
	}

}
