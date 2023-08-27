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
package org.springframework.samples.petclinic.vet.application;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.VetService;

/**
 * A DDD Application Service
 *
 * @author github.com/abchau
 */
@Service
public class VetFacade {

	private final VetService vetService;

	public VetFacade(VetService vetService) {
		this.vetService = vetService;
	}

	@Transactional(readOnly = true)
	public Collection<Vet> findAll() {
		return vetService.findAll();
	
	}

	@Transactional(readOnly = true)
	public Page<Vet> findAll(int page) {
		return vetService.findPaginatedVet(page);
	}

}
