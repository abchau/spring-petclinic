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
package org.springframework.samples.petclinic.pet.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.OwnerRepository;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

/**
 * Technology-specific provider of a DDD Repository
 *
 * @author github.com/abchau
 */
@Repository
class OwnerRepositoryImpl implements OwnerRepository {

	private final OwnerEntityRepository ownerEntityRepository;

	@Autowired
	public OwnerRepositoryImpl(OwnerEntityRepository ownerEntityRepository) {
		this.ownerEntityRepository = ownerEntityRepository;
	}

	@Override
	public Owner create(Owner newOwner) {
		OwnerEntity newOwnerEntity = OwnerEntity.translateToPersistenceModel.apply(newOwner);
		OwnerEntity createdOwnerEntity = ownerEntityRepository.save(newOwnerEntity);
		Owner savedOwner = OwnerEntity.translateToDomainModel.apply(createdOwnerEntity);
		return savedOwner;
	}

	@Override
	public Owner update(Owner updatedOwner) {
		OwnerEntity updatedOwnerEntity = OwnerEntity.translateToPersistenceModel.apply(updatedOwner);
		OwnerEntity savedOwnerEntity = ownerEntityRepository.save(updatedOwnerEntity);
		Owner savedOwner = OwnerEntity.translateToDomainModel.apply(savedOwnerEntity);
		return savedOwner;
	}

	@Override
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
	public Owner findById(Integer id) {
		OwnerEntity ownerEntity = ownerEntityRepository.findById(id);
		Owner owner = OwnerEntity.translateToDomainModel.apply(ownerEntity);
		return owner;
	}

	@Override
	public Owner findWithPetsById(Integer id) {
		OwnerEntity ownerEntity = ownerEntityRepository.findWithPetsById(id);
		Owner owner = OwnerEntity.translateToDomainModel.apply(ownerEntity);
		return owner;
	}

}
