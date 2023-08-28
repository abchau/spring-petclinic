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
import org.springframework.samples.petclinic.pet.infrastructure.PetEntity;
import org.springframework.samples.petclinic.pet.infrastructure.PetEntityRepository;
import org.springframework.samples.petclinic.pet.application.PetEntityTranslator;
import org.springframework.samples.petclinic.pet.infrastructure.PetTypeEntity;
import org.springframework.samples.petclinic.pet.infrastructure.PetTypeEntityRepository;
import org.springframework.samples.petclinic.pet.infrastructure.PetTypeEntityTranslator;

/**
 * A traditional service class
 *
 * @author github.com/abchau
 */
@Service
public class PetService {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

	@Autowired
	public PetService(PetEntityRepository petEntityRepository,
			PetTypeEntityRepository petTypeEntityRepository) {
		this.petEntityRepository = petEntityRepository;
		this.petTypeEntityRepository = petTypeEntityRepository;
	}

	@Transactional
	public Pet createPet(Pet newPet) {
		PetEntity newPetEntity = PetEntityTranslator.toPersistenceModel(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = PetEntityTranslator.toDomainModel(savedPetEntity);
		return savedPet;
	}

	@Transactional
	public Pet updatePet(Pet updatedPet) {
		PetEntity updatedPetEntity = PetEntityTranslator.toPersistenceModel(updatedPet);
		PetEntity savedPetEntity = petEntityRepository.save(updatedPetEntity);
		Pet savedPet = PetEntityTranslator.toDomainModel(savedPetEntity);
		return savedPet;
	}

	@Transactional(readOnly = true)
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId)
			.stream()
			.map(PetEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Pet findById(Integer id) {
		PetEntity petEntity = petEntityRepository.findById(id);
		Pet pet = PetEntityTranslator.toDomainModel(petEntity);
		return pet;
	}

	@Transactional(readOnly = true)
	public Pet findWithVisitsById(Integer id) {
		return petRepository.findWithVisitsById(id);
	}

	@Transactional(readOnly = true)
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll()
			.stream()
			.map(PetTypeEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

}
