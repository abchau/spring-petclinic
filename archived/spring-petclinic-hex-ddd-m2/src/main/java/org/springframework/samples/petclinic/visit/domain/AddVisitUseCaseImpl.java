package org.springframework.samples.petclinic.visit.domain;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class AddVisitUseCaseImpl implements AddVisitUseCase {

	private final VisitRepository visitRepository;

	@Autowired
	public AddVisitUseCaseImpl(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	@Override
	public Visit addVisit(Visit visit) {
		return visitRepository.create(visit);
	}

}
