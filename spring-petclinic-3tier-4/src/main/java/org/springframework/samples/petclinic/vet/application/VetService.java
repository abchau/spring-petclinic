package org.springframework.samples.petclinic.vet.application;

import java.util.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;
import org.springframework.samples.petclinic.vet.domain.VetRepository;

@Service
public class VetService {

	private final VetRepository vetRepository;

	public VetService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Vet> findAll() {
		return vetRepository.findAll();
	
	}

	@Transactional(readOnly = true)
	public PaginatedVet findAll(int page) {
		return vetRepository.findPaginatedVet(page);
	}

}
