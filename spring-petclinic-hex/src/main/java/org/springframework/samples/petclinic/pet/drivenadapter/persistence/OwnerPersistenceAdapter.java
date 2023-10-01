package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.pet.application.Owner;
import org.springframework.samples.petclinic.pet.application.Owner.PaginatedOwner;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveOwnerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryAdapter
@Service
class OwnerPersistenceAdapter implements LoadOwnerPort, SaveOwnerPort {

	private final OwnerEntityRepository ownerEntityRepository;

	public OwnerPersistenceAdapter(OwnerEntityRepository ownerEntityRepository) {
		this.ownerEntityRepository = ownerEntityRepository;
	}

	@Override
	@Transactional
	public Owner create(Owner newOwner) {
		OwnerEntity newOwnerEntity = translateToPersistenceModel(newOwner);
		OwnerEntity createdOwnerEntity = ownerEntityRepository.save(newOwnerEntity);
		Owner savedOwner = translateToDomainModel(createdOwnerEntity);
		return savedOwner;
	}

	@Override
	@Transactional
	public Owner update(Owner updatedOwner) {
		OwnerEntity ownerEntity = ownerEntityRepository.findById(updatedOwner.getId());
		ownerEntity.setFirstName(updatedOwner.getFirstName());
		ownerEntity.setLastName(updatedOwner.getLastName());
		ownerEntity.setAddress(updatedOwner.getAddress());
		ownerEntity.setCity(updatedOwner.getCity());
		ownerEntity.setTelephone(updatedOwner.getTelephone());

		OwnerEntity savedOwnerEntity = ownerEntityRepository.save(ownerEntity);

		Owner savedOwner = translateToDomainModel(savedOwnerEntity);
		return savedOwner;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedOwner findByLastName(String lastname, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<OwnerEntity> jpaEntities = ownerEntityRepository.findByLastName(lastname, pageable);

		List<Owner> owners = jpaEntities.stream()
			.map(OwnerPersistenceAdapter::translateToDomainModel)
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
		Owner owner = translateToDomainModel(ownerEntity);
		return owner;
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findWithPetsById(Integer ownerId) {
		OwnerEntity ownerEntity = ownerEntityRepository.findWithPetsById(ownerId);
		Owner owner = translateToDomainModel(ownerEntity);
		return owner;
	}

	static Owner translateToDomainModel(OwnerEntity ownerEntity) {
		Owner owner = new Owner();
		owner.setId(ownerEntity.getId());
		owner.setFirstName(ownerEntity.getFirstName());
		owner.setLastName(ownerEntity.getLastName());
		owner.setAddress(ownerEntity.getAddress());
		owner.setCity(ownerEntity.getCity());
		owner.setTelephone(ownerEntity.getTelephone());
		if (ownerEntity.getPets() != null) {
			owner.setPets(
				ownerEntity.getPets().stream()
					.map(PetPersistenceAdapter::translateToDomainModel)
					.collect(Collectors.toList()));
		}
		return owner;
	}

	static OwnerEntity translateToPersistenceModel(Owner owner) {
		OwnerEntity ownerEntity = new OwnerEntity();
		ownerEntity.setId(owner.getId());
		ownerEntity.setFirstName(owner.getFirstName());
		ownerEntity.setLastName(owner.getLastName());
		ownerEntity.setAddress(owner.getAddress());
		ownerEntity.setCity(owner.getCity());
		ownerEntity.setTelephone(owner.getTelephone());

		return ownerEntity;
	}
}
