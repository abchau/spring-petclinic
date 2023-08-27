package org.springframework.samples.petclinic.vet.domain.vet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.vet.domain.vet.Vet.PaginatedVet;
import org.springframework.stereotype.Component;

// API
@Component
class ShowVetUseCaseImpl implements ShowVetUseCase {

	private final LoadVetUseCase loadVetUseCase;

	@Autowired
	public ShowVetUseCaseImpl(LoadVetUseCase loadVetUseCase) {
		this.loadVetUseCase = loadVetUseCase;
	}

	@Override
	public List<Vet> findAll() {
		return loadVetUseCase.findAll();
	}

	@Override
	public PaginatedVet findPaginatedVet(int page) {
		return loadVetUseCase.findPaginatedVet(page);
	}

}
