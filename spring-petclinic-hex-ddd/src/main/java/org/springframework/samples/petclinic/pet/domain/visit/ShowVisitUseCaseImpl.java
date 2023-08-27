package org.springframework.samples.petclinic.pet.domain.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ShowVisitUseCaseImpl implements ShowVisitUseCase {

	private final LoadVisitUseCase loadVisitUseCase;

	@Autowired
	public ShowVisitUseCaseImpl(LoadVisitUseCase loadVisitUseCase) {
		this.loadVisitUseCase = loadVisitUseCase;
	}

	@Override
	public List<Visit> findVisits(Integer petId) {
		return loadVisitUseCase.findAllByPetId(petId);
	}

}
