package org.springframework.samples.petclinic.vet.application;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class VetFacadeImpl implements VetFacade {

	private final ShowVetUseCase showVetUseCase;

	public VetFacadeImpl(ShowVetUseCase showVetUseCase) {
		this.showVetUseCase = showVetUseCase;
	}

	@Transactional(readOnly = true)
	public List<Vet> findAll() {
		return showVetUseCase.findAll();
	}

	@Transactional(readOnly = true)
	public PaginatedVet findPaginatedVet(int page) {
		return showVetUseCase.findPaginatedVet(page);
	}

}
