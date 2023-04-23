package org.springframework.samples.petclinic.vet.infrastructure;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It can be a local transaction or remote RPC to other services, cloud services, etc
 */
@Service
public class VetRepositoryProvider implements VetRepository {

	private final VetEntityRepository vetEntityRepository;

	public VetRepositoryProvider(VetEntityRepository vetEntityRepository) {
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
