package org.springframework.samples.petclinic.pet.domain.visit;

import org.springframework.beans.factory.annotation.Autowired;
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
