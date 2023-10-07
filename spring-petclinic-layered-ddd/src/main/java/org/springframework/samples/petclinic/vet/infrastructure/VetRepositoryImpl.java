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
package org.springframework.samples.petclinic.vet.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.VetRepository;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;

/**
 * Technology-specific provider of a DDD Repository
 *
 * @author github.com/abchau
 */
@Service
 class VetRepositoryImpl implements VetRepository {

	private final VetEntityRepository vetEntityRepository;

	public VetRepositoryImpl(VetEntityRepository vetEntityRepository) {
		this.vetEntityRepository = vetEntityRepository;
	}

	@Override
	public List<Vet> findAll() {
		return vetEntityRepository.findAll()
			.stream()
			.map(VetEntity.translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	public PaginatedVet findPaginatedVet(int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);

		Page<VetEntity> jpaEntities = vetEntityRepository.findAll(pageable);
		List<Vet> vets = jpaEntities.stream()
			.map(VetEntity.translateToDomainModel)
			.collect(Collectors.toList());

		Page<Vet> result = new PageImpl<Vet>(vets, pageable, jpaEntities.getTotalElements());

		PaginatedVet paginatedVet = new PaginatedVet();
		paginatedVet.setTotalPages(result.getTotalPages());
		paginatedVet.setTotalElements(result.getTotalElements());
		paginatedVet.setContent(vets);

		return paginatedVet;
	}

}
