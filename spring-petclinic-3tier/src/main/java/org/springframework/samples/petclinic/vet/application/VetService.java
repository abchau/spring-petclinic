package org.springframework.samples.petclinic.vet.application;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.infrastructure.VetRepository;

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
	public Page<Vet> findAll(int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);

		return vetRepository.findAll(pageable);
	}

}
