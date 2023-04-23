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
package org.springframework.samples.petclinic.owner.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.domain.Pet;
import org.springframework.samples.petclinic.owner.domain.PetType;
import org.springframework.samples.petclinic.owner.infrastructure.PetRepository;
import org.springframework.samples.petclinic.owner.infrastructure.PetTypeRepository;
import org.springframework.stereotype.Service;

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

	public Pet createPet(Pet pet) {
		return petRepository.save(pet);
	}

	public Pet updatePet(Pet pet) {
		System.out.println("pet: " + pet);
		return petRepository.save(pet);
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
		return petTypeRepository.findAll();
	}

}
