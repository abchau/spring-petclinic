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
package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.Visit;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveVisitPort;
import org.springframework.samples.petclinic.pet.application.drivingport.AddVisitPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Service
class ArchitectureTests implements AddVisitPort {

	private final SaveVisitPort saveVisitPort;

	@Autowired
	public ArchitectureTests(SaveVisitPort saveVisitPort) {
		this.saveVisitPort = saveVisitPort;
	}

	@Transactional
	@Override
	public Visit addVisit(Visit visit) {
		return saveVisitPort.create(visit);
	}

}
