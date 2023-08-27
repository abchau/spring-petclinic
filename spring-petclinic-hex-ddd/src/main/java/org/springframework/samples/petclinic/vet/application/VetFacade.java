package org.springframework.samples.petclinic.vet.application;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.vet.ShowVetUseCase;
import org.springframework.samples.petclinic.vet.domain.vet.Vet;
import org.springframework.samples.petclinic.vet.domain.vet.Vet.PaginatedVet;
import org.springframework.stereotype.Service;

@Service
public class VetFacade {

	private final ShowVetUseCase showVetUseCase;

	public VetFacade(ShowVetUseCase showVetUseCase) {
		this.showVetUseCase = showVetUseCase;
	}

	public List<Vet> findAll() {
		return showVetUseCase.findAll();
	}

	public PaginatedVet findPaginatedVet(int page) {
		return showVetUseCase.findPaginatedVet(page);
	}

}
