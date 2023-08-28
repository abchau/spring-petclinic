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
package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.infrastructure.OwnerEntity;
import org.springframework.samples.petclinic.pet.infrastructure.OwnerEntityRepository;
import org.springframework.samples.petclinic.pet.infrastructure.OwnerEntityTranslator;

/**
 * A traditional service class
 *
 * @author github.com/abchau
 */
@Service
public class OwnerService {

	private final OwnerEntityRepository ownerEntityRepository;

	@Autowired
	public OwnerService(OwnerEntityRepository ownerEntityRepository) {
		this.ownerEntityRepository = ownerEntityRepository;
	}

	@Transactional
	public Owner createOwner(Owner newOwner) {
		OwnerEntity newOwnerEntity = OwnerEntityTranslator.toPersistenceModel(newOwner);
		OwnerEntity createdOwnerEntity = ownerEntityRepository.save(newOwnerEntity);
		Owner savedOwner = OwnerEntityTranslator.toDomainModel(createdOwnerEntity);
		return savedOwner;
	}

	@Transactional
	public Owner updateOwner(Owner updatedOwner) {
		OwnerEntity updatedOwnerEntity = OwnerEntityTranslator.toPersistenceModel(updatedOwner);
		OwnerEntity savedOwnerEntity = ownerEntityRepository.save(updatedOwnerEntity);
		Owner savedOwner = OwnerEntityTranslator.toDomainModel(savedOwnerEntity);
		return savedOwner;
	}

	@Transactional(readOnly = true)
	public Page<Owner> findPaginatedForOwnersLastName(String lastname, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<OwnerEntity> jpaEntities = ownerEntityRepository.findByLastName(lastname, pageable);

		List<Owner> owners = jpaEntities.stream()
			.map(OwnerEntityTranslator::toDomainModel)
			.collect(Collectors.toList());

		return new PageImpl<Owner>(owners, pageable, jpaEntities.getTotalElements());
	}

	@Transactional(readOnly = true)
	public Owner findById(Integer id) {
		OwnerEntity ownerEntity = ownerEntityRepository.findById(id);
		Owner owner = OwnerEntityTranslator.toDomainModel(ownerEntity);
		return owner;
	}

	@Transactional(readOnly = true)
	public Owner findWithPetsById(Integer id) {
		OwnerEntity ownerEntity = ownerEntityRepository.findWithPetsById(id);
		Owner owner = OwnerEntityTranslator.toDomainModel(ownerEntity);
		return owner;
	}

}
