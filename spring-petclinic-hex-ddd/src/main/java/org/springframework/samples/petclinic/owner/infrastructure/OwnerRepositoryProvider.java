package org.springframework.samples.petclinic.owner.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.owner.domain.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a DDD Repository Domain SPI
 *
 * It can be a local transaction or RPC to other services, loud services, etc
 *
 * @author github.com/abchau
 */
@Service
public class OwnerRepositoryProvider implements OwnerRepository {

	private final OwnerEntityRepository ownerEntityRepository;

	public OwnerRepositoryProvider(OwnerEntityRepository ownerEntityRepository) {
		this.ownerEntityRepository = ownerEntityRepository;
	}

	@Override
	@Transactional
	public Owner create(Owner newOwner) {
		OwnerEntity newOwnerEntity = OwnerEntityTranslator.toPersistenceModel(newOwner);
		OwnerEntity createdOwnerEntity = ownerEntityRepository.save(newOwnerEntity);
		Owner savedOwner = OwnerEntityTranslator.toDomainModel(createdOwnerEntity);
		return savedOwner;
	}

	@Override
	@Transactional
	public Owner update(Owner updatedOwner) {
		OwnerEntity updatedOwnerEntity = OwnerEntityTranslator.toPersistenceModel(updatedOwner);
		OwnerEntity savedOwnerEntity = ownerEntityRepository.save(updatedOwnerEntity);
		Owner savedOwner = OwnerEntityTranslator.toDomainModel(savedOwnerEntity);
		return savedOwner;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedOwner findByLastName(String lastname, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<OwnerEntity> jpaEntities = ownerEntityRepository.findByLastName(lastname, pageable);

		List<Owner> owners = jpaEntities.stream()
			.map(OwnerEntityTranslator::toDomainModel)
			.collect(Collectors.toList());

		Page<Owner> result = new PageImpl<Owner>(owners, pageable, jpaEntities.getTotalElements());

		PaginatedOwner paginatedOwner = new PaginatedOwner();
		paginatedOwner.setTotalPages(result.getTotalPages());
		paginatedOwner.setTotalElements(result.getTotalElements());
		paginatedOwner.setContent(owners);

		return paginatedOwner;
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findById(Integer ownerId) {
		OwnerEntity ownerEntity = ownerEntityRepository.findById(ownerId);
		Owner owner = OwnerEntityTranslator.toDomainModel(ownerEntity);
		return owner;
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findWithPetsById(Integer ownerId) {
		OwnerEntity ownerEntity = ownerEntityRepository.findWithPetsById(ownerId);
		Owner owner = OwnerEntityTranslator.toDomainModel(ownerEntity);
		return owner;
	}

}
