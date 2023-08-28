package org.springframework.samples.petclinic.vet.application;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;

// API
public interface VetFacade {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
