package org.springframework.samples.petclinic.vet.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.vet.domain.VetRepository.PaginatedVet;
import org.springframework.stereotype.Component;

// API
@Component
class ShowVetUseCaseImpl implements ShowVetUseCase {

	private final VetRepository vetRepository;

	@Autowired
	public ShowVetUseCaseImpl(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Override
	public List<Vet> findAll() {
		return vetRepository.findAll();
	}

	@Override
	public PaginatedVet findPaginatedVet(int page) {
		return vetRepository.findPaginatedVet(page);
	}

}
