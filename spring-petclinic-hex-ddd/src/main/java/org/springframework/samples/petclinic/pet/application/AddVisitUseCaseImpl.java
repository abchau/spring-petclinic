package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.visit.AddVisitUseCase;
import org.springframework.samples.petclinic.pet.domain.visit.SaveVisitUseCase;
import org.springframework.samples.petclinic.pet.domain.visit.Visit;
import org.springframework.stereotype.Component;

@Component
class AddVisitUseCaseImpl implements AddVisitUseCase {

	private final SaveVisitUseCase saveVisitUseCase;

	@Autowired
	public AddVisitUseCaseImpl(SaveVisitUseCase saveVisitUseCase) {
		this.saveVisitUseCase = saveVisitUseCase;
	}

	@Override
	public Visit addVisit(Visit visit) {
		return saveVisitUseCase.create(visit);
	}

}
