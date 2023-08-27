package org.springframework.samples.petclinic.vet.application;

import java.util.Collection;
import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.infrastructure.VetEntity;
import org.springframework.samples.petclinic.vet.infrastructure.VetEntityRepository;
import org.springframework.samples.petclinic.vet.infrastructure.VetEntityTranslator;

@Service
class VetService {

	public final VetEntityRepository vetEntityRepository;

	public VetService(VetEntityRepository vetEntityRepository) {
		this.vetEntityRepository = vetEntityRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Vet> findAll() {
		return vetEntityRepository.findAll()
				.map(VetEntityTranslator::toDomainModel)
				.collect(Collections.toList());
	
	}

	@Transactional(readOnly = true)
	public Page<Vet> findAll(int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);

		Page<VetEntity> jpaEntities = vetEntityRepository.findAll(pageable);
		List<Vet> vets = jpaEntities.stream()
			.map(VetEntityTranslator::toDomainModel)
			.collect(Collectors.toList());

		return new PageImpl<Vet>(vets, pageable, jpaEntities.getTotalElements());
	}

}
