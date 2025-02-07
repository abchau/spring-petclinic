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

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetRepository;
import org.springframework.samples.petclinic.pet.domain.PetType;

/**
 * Technology-specific provider of a DDD Repository
 *
 * @author github.com/abchau
 */
@Repository
class PetRepositoryImpl implements PetRepository {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

	@Autowired
	public PetRepositoryImpl(PetEntityRepository petEntityRepository,
			PetTypeEntityRepository petTypeEntityRepository) {
		this.petEntityRepository = petEntityRepository;
		this.petTypeEntityRepository = petTypeEntityRepository;
	}

	@Override
	public Pet create(Pet newPet) {
		PetEntity newPetEntity = PetEntity.translateToPersistenceModel.apply(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = PetEntity.translateToDomainModel.apply(savedPetEntity);
		return savedPet;
	}

	@Override
	public Pet update(Pet updatedPet) {
		PetEntity updatedPetEntity = PetEntity.translateToPersistenceModel.apply(updatedPet);
		PetEntity savedPetEntity = petEntityRepository.save(updatedPetEntity);
		Pet savedPet = PetEntity.translateToDomainModel.apply(savedPetEntity);
		return savedPet;
	}

	@Override
	public Pet findById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findById(petId);
		Pet pet = PetEntity.translateToDomainModel.apply(petEntity);
		return pet;
	}

	@Override
	public Pet findWithVisitsById(Integer id) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(id);
		Pet pet = PetEntity.translateToDomainModel.apply(petEntity);
		return pet;
	}

	@Override
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId)
			.stream()
			.map(PetEntity.translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll()
			.stream()
			.map(PetTypeEntity.translateToDomainModel)
			.collect(Collectors.toList());
	}

}
