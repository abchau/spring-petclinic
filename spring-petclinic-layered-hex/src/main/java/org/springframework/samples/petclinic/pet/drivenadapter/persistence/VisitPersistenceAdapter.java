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
import org.springframework.samples.petclinic.pet.application.Visit;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadVisitPort;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveVisitPort;
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
class VisitPersistenceAdapter implements LoadVisitPort, SaveVisitPort {

	private final VisitEntityRepository visitEntityRepository;

	public VisitPersistenceAdapter(VisitEntityRepository visitEntityRepository) {
		this.visitEntityRepository = visitEntityRepository;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Visit create(Visit newVisit) {
		VisitEntity newVisitEntity = VisitEntity.translateToPersistenceModel.apply(newVisit);
		VisitEntity savedVisitEntity = visitEntityRepository.save(newVisitEntity);
		Visit savedVisit = VisitEntity.translateToDomainModel.apply(savedVisitEntity);
		return savedVisit;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public List<Visit> findAllByPetId(Integer petId) {
		return visitEntityRepository.findAllByPetId(petId)
			.stream()
			.map(VisitEntity.translateToDomainModel)
			.collect(Collectors.toList());
	}

}
