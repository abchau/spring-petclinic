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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.OwnerService;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

@Service
non-sealed class OwnerFacadeImpl implements OwnerFacade {

	private final OwnerService ownerService;

	@Autowired
	public OwnerFacadeImpl(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@Transactional
	public Owner createOwner(Owner newOwner) {
		return ownerService.createOwner(newOwner);
	}

	@Transactional
	public Owner updateOwner(Owner updatedOwner) {
		return ownerService.updateOwner(updatedOwner);
	}

	@Transactional(readOnly = true)
	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page) {
		return ownerService.findPaginatedForOwnersLastName(lastname, page);
	}

	@Transactional(readOnly = true)
	public Owner findById(Integer id) {
		return ownerService.findById(id);
	}

	@Transactional(readOnly = true)
	public Owner findWithPetsById(Integer id) {
		return ownerService.findWithPetsById(id);
	}

}
