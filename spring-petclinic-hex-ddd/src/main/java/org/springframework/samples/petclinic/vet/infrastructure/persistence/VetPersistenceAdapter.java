package org.springframework.samples.petclinic.vet.infrastructure.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.domain.vet.Vet;
import org.springframework.samples.petclinic.vet.domain.vet.Vet.PaginatedVet;
import org.springframework.samples.petclinic.vet.domain.vet.LoadVetUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It can be a local transaction or remote RPC to other services, cloud services, etc
 */
@Service
class VetPersistenceAdapter implements LoadVetUseCase {

	private final VetEntityRepository vetEntityRepository;

	public VetPersistenceAdapter(VetEntityRepository vetEntityRepository) {
		this.vetEntityRepository = vetEntityRepository;
	}

	@Override
	public List<Vet> findAll() {
		return vetEntityRepository.findAll().stream()
			.map(VetEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedVet findPaginatedVet(int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);
		
		Page<VetEntity> jpaEntities = vetEntityRepository.findAll(pageable);
		List<Vet> vets = jpaEntities.stream().map(VetEntityTranslator::toDomainModel).collect(Collectors.toList());

		Page<Vet> result = new PageImpl<Vet>(vets, pageable, jpaEntities.getTotalElements());

		PaginatedVet paginatedVet = new PaginatedVet();
		paginatedVet.setTotalPages(result.getTotalPages());
		paginatedVet.setTotalElements(result.getTotalElements());
		paginatedVet.setContent(vets);

		return paginatedVet;
	}

}
