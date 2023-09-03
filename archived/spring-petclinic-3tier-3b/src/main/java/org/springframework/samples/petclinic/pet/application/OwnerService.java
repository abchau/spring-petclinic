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

import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.OwnerRepository;

/**
 * A traditional service class
 *
 * @author github.com/abchau
 */
@Service
public class OwnerService {

	private final OwnerRepository ownerRepository;

	@Autowired
	public OwnerService(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Transactional
	public Owner createOwner(Owner newOwner) {
		return ownerRepository.create(newOwner);
	}

	@Transactional
	public Owner updateOwner(Owner updatedOwner) {
		return ownerRepository.update(updatedOwner);
	}

	@Transactional(readOnly = true)
	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page) {
		return ownerRepository.findByLastName(lastname, page);
	}

	@Transactional(readOnly = true)
	public Owner findById(Integer id) {
		return ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Owner findWithPetsById(Integer id) {
		return ownerRepository.findWithPetsById(id);
	}

}
