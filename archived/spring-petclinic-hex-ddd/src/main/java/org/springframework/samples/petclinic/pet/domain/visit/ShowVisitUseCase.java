package org.springframework.samples.petclinic.pet.domain.visit;

import java.util.List;

public interface ShowVisitUseCase {

	public List<Visit> findVisits(Integer petId);

}
