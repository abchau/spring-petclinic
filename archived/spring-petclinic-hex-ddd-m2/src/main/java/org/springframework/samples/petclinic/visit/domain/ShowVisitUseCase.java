package org.springframework.samples.petclinic.visit.domain;

import java.util.List;

public interface ShowVisitUseCase {

	public List<Visit> findVisits(Integer petId);

}
