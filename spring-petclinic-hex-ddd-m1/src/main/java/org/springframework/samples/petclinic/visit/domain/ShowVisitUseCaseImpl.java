package org.springframework.samples.petclinic.visit.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ShowVisitUseCaseImpl implements ShowVisitUseCase {

	private final VisitRepository visitRepository;

	@Autowired
	public ShowVisitUseCaseImpl(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	@Override
	public List<Visit> findVisits(Integer petId) {
		return visitRepository.findAllByPetId(petId);
	}

}
