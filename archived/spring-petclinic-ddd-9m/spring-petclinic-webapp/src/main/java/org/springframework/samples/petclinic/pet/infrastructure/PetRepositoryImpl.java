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
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetRepository;
import org.springframework.samples.petclinic.pet.domain.PetType;

/**
 * It can be a local transaction or RPC to other services, loud services, etc
 */
@Service
class PetRepositoryProvider implements PetRepository {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

	@Autowired
	public PetRepositoryProvider(PetEntityRepository petEntityRepository,
			PetTypeEntityRepository petTypeEntityRepository) {
		this.petEntityRepository = petEntityRepository;
		this.petTypeEntityRepository = petTypeEntityRepository;
	}

	@Override
	public Pet create(Pet newPet) {
		PetEntity newPetEntity = PetEntityTranslator.toPersistenceModel(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = PetEntityTranslator.toDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	public Pet update(Pet updatedPet) {
		PetEntity updatedPetEntity = PetEntityTranslator.toPersistenceModel(updatedPet);
		PetEntity savedPetEntity = petEntityRepository.save(updatedPetEntity);
		Pet savedPet = PetEntityTranslator.toDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	public Pet findById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findById(petId);
		Pet pet = PetEntityTranslator.toDomainModel(petEntity);
		return pet;
	}

	@Override
	public Pet findWithVisitsById(Integer id) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(id);
		Pet pet = PetEntityTranslator.toDomainModel(petEntity);
		return pet;
	}

	@Override
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId)
			.stream()
			.map(PetEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll()
			.stream()
			.map(PetTypeEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

}
