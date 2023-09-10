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
package org.springframework.samples.petclinic.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.pet.dao.PetRepository;
import org.springframework.samples.petclinic.pet.dao.PetTypeRepository;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;

/**
 * A traditional service class
 *
 * @author github.com/abchau
 */
@Service
public class PetService {

	private final PetRepository petRepository;

	private final PetTypeRepository petTypeRepository;

	@Autowired
	public PetService(PetRepository petRepository, PetTypeRepository petTypeRepository) {
		this.petRepository = petRepository;
		this.petTypeRepository = petTypeRepository;
	}

	@Transactional
	public Pet createPet(Pet pet) {
		return petRepository.save(pet);
	}

	@Transactional
	public Pet updatePet(Pet pet) {
		return petRepository.save(pet);
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
		return petRepository.findWithVisitsById(id);
	}

	@Transactional(readOnly = true)
	public List<PetType> findPetTypes() {
		return petTypeRepository.findAll();
	}

}
