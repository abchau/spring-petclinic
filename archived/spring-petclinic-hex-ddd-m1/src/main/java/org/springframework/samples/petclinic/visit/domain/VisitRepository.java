package org.springframework.samples.petclinic.visit.domain;

import java.util.List;

// SPI
public interface VisitRepository {

	public Visit create(Visit visit);

	public List<Visit> findAllByPetId(Integer petId);

}
