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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.pet.domain.model.Visit;
import org.springframework.samples.petclinic.pet.domain.service.VisitRepository;

/**
 * A DDD Application Service
 *
 * @author github.com/abchau
 */
@Service
class VisitFacadeImpl implements VisitFacade {

	private final VisitRepository visitRepository;

	@Autowired
	public VisitFacadeImpl(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	public Visit addVisit(Visit newVisit) {
		return visitRepository.create(newVisit);
	}

	public List<Visit> findVisits(Integer petId) {
		return visitRepository.findAllByPetId(petId);
	}

}
