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

import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetType;
import org.springframework.samples.petclinic.pet.domain.PetRepository;

/**
 * A traditional service class
 *
 * @author github.com/abchau
 */
@Service
public class PetService {

	private final PetRepository petRepository;

	@Autowired
	public PetService(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Transactional
	public Pet createPet(Pet newPet) {
		return petRepository.create(newPet);
	}

	@Transactional
	public Pet updatePet(Pet updatedPet) {
		return petRepository.update(updatedPet);
	}

	@Transactional(readOnly = true)
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petRepository.findAllByOwnerId(ownerId);
	}

	@Transactional(readOnly = true)
	public Pet findById(Integer id) {
		return petRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Pet findWithVisitsById(Integer id) {
		return petRepository.findWithVisitsById(ownerId);
	}

	@Transactional(readOnly = true)
	public List<PetType> findPetTypes() {
		return petRepository.findPetTypes();
	}

}
