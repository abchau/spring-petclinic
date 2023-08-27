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
package org.springframework.samples.petclinic.pet.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * A DDD Domain Service
 *
 * @author github.com/abchau
 */
@Component
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;

	@Autowired
	public PetService(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	public Pet createPet(Pet newPet) {
		return petRepository.create(newPet);
	}

	public Pet updatePet(Pet updatedPet) {
		return petRepository.update(updatedPet);
	}

	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petRepository.findAllByOwnerId(ownerId);
	}

	public Pet findById(Integer id) {
		return petRepository.findById(id);
	}

	public Pet findWithVisitsById(Integer id) {
		return petRepository.findWithVisitsById(id);
	}

	public List<PetType> findPetTypes() {
		return petRepository.findPetTypes();
	}

}
