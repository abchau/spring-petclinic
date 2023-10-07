/*
 * Copyright 2023- the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryAdapter
@Repository
class OwnerPersistenceAdapter implements LoadOwnerPort, SaveOwnerPort {

	private final OwnerEntityRepository ownerEntityRepository;

	public OwnerPersistenceAdapter(OwnerEntityRepository ownerEntityRepository) {
		this.ownerEntityRepository = ownerEntityRepository;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Owner create(Owner newOwner) {
		OwnerEntity newOwnerEntity = OwnerEntity.translateToPersistenceModel.apply(newOwner);
		OwnerEntity createdOwnerEntity = ownerEntityRepository.save(newOwnerEntity);
		Owner savedOwner = OwnerEntity.translateToDomainModel.apply(createdOwnerEntity);
		return savedOwner;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Owner update(Owner updatedOwner) {
		OwnerEntity ownerEntity = ownerEntityRepository.findById(updatedOwner.getId());
		ownerEntity.setFirstName(updatedOwner.getFirstName());
		ownerEntity.setLastName(updatedOwner.getLastName());
		ownerEntity.setAddress(updatedOwner.getAddress());
		ownerEntity.setCity(updatedOwner.getCity());
		ownerEntity.setTelephone(updatedOwner.getTelephone());

		OwnerEntity savedOwnerEntity = ownerEntityRepository.save(ownerEntity);

		Owner savedOwner = OwnerEntity.translateToDomainModel.apply(savedOwnerEntity);
		return savedOwner;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public PaginatedOwner findByLastName(String lastname, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<OwnerEntity> jpaEntities = ownerEntityRepository.findByLastName(lastname, pageable);

		List<Owner> owners = jpaEntities.stream()
			.map(OwnerEntity.translateToDomainModel)
			.collect(Collectors.toList());

		Page<Owner> result = new PageImpl<Owner>(owners, pageable, jpaEntities.getTotalElements());

		PaginatedOwner paginatedOwner = new PaginatedOwner();
		paginatedOwner.setTotalPages(result.getTotalPages());
		paginatedOwner.setTotalElements(result.getTotalElements());
		paginatedOwner.setContent(owners);

		return paginatedOwner;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Owner findById(Integer ownerId) {
		OwnerEntity ownerEntity = ownerEntityRepository.findById(ownerId);
		Owner owner = OwnerEntity.translateToDomainModel.apply(ownerEntity);
		return owner;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Owner findWithPetsById(Integer ownerId) {
		OwnerEntity ownerEntity = ownerEntityRepository.findWithPetsById(ownerId);
		Owner owner = OwnerEntity.translateToDomainModel.apply(ownerEntity);
		return owner;
	}

}
