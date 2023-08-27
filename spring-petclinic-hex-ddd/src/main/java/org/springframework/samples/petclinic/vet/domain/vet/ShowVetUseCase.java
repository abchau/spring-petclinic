package org.springframework.samples.petclinic.vet.domain.vet;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.vet.Vet.PaginatedVet;

public interface ShowVetUseCase {

	public List<Vet> findAll();
	
	public PaginatedVet findPaginatedVet(int page);

}
