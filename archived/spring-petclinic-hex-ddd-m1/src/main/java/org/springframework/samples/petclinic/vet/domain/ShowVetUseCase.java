package org.springframework.samples.petclinic.vet.domain;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.VetRepository.PaginatedVet;

public interface ShowVetUseCase {

	public List<Vet> findAll();
	
	public PaginatedVet findPaginatedVet(int page);

}
