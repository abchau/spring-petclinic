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
package org.springframework.samples.petclinic.vet.application.businesslogic;

import java.util.List;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.vet.application.Vet;
import org.springframework.samples.petclinic.vet.application.Vet.PaginatedVet;
import org.springframework.samples.petclinic.vet.application.drivenport.LoadVetPort;
import org.springframework.samples.petclinic.vet.application.drivingport.ShowVetPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Service
class ShowVetUseCase implements ShowVetPort {

	private final LoadVetPort loadVetPort;

	@Autowired
	public ShowVetUseCase(LoadVetPort loadVetPort) {
		this.loadVetPort = loadVetPort;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Vet> findAll() {
		return loadVetPort.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public PaginatedVet findPaginatedVet(int page) {
		return loadVetPort.findPaginatedVet(page);
	}

}
