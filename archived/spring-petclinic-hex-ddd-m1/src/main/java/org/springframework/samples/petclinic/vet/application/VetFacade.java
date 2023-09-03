package org.springframework.samples.petclinic.vet.application;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.ShowVetUseCase;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.VetRepository.PaginatedVet;
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
