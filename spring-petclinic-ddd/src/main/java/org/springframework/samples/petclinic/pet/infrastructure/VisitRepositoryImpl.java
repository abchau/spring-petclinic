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

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.PetType;
import org.springframework.samples.petclinic.pet.domain.Visit;
import org.springframework.samples.petclinic.pet.domain.VisitRepository;

/**
 * Technology-specific provider of a DDD Repository
 *
 * @author github.com/abchau
 */
@Service
class VisitRepositoryImpl implements VisitRepository {

	private final VisitEntityRepository visitEntityRepository;

	@Autowired
	public VisitRepositoryImpl(VisitEntityRepository visitEntityRepository) {
		this.visitEntityRepository = visitEntityRepository;
	}

	@Override
	public Visit create(Visit newVisit) {
		VisitEntity newVisitEntity = VisitRepositoryImpl.translateToPersistenceModel(newVisit);
		VisitEntity savedVisitEntity = visitEntityRepository.save(newVisitEntity);
		Visit savedVisit = VisitRepositoryImpl.translateToDomainModel(savedVisitEntity);
		return savedVisit;
	}

	@Override
	public List<Visit> findAllByPetId(Integer petId) {
		return visitEntityRepository.findAllByPetId(petId)
			.stream()
			.map(VisitRepositoryImpl::translateToDomainModel)
			.collect(Collectors.toList());
	}

	static Visit translateToDomainModel(VisitEntity visitEntity) {
		Visit visit = new Visit();
		visit.setId(visitEntity.getId());
		visit.setPetId(visitEntity.getPetId());
		visit.setDate(visitEntity.getDate());
		visit.setDescription(visitEntity.getDescription());

		return visit;
	}

	static VisitEntity translateToPersistenceModel(Visit visit) {
		VisitEntity visitEntity = new VisitEntity();
		visitEntity.setId(visit.getId());
		visitEntity.setPetId(visit.getPetId());
		visitEntity.setDate(visit.getDate());
		visitEntity.setDescription(visit.getDescription());

		return visitEntity;
	}

	static PetType translateToDomainModel(PetTypeEntity petTypeEntity) {
		PetType petType = new PetType();
		petType.setId(petTypeEntity.getId());
		petType.setName(petTypeEntity.getName());

		return petType;
	}

}
