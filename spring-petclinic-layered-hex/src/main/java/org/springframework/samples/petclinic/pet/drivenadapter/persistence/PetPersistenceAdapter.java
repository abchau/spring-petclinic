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
package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.PetType;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadPetPort;
import org.springframework.samples.petclinic.pet.application.drivenport.SavePetPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryAdapter
@Repository
class PetPersistenceAdapter implements LoadPetPort, SavePetPort {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

	public PetPersistenceAdapter(PetEntityRepository petEntityRepository,
			PetTypeEntityRepository petTypeEntityRepository) {
		this.petEntityRepository = petEntityRepository;
		this.petTypeEntityRepository = petTypeEntityRepository;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Pet create(Pet newPet) {
		PetEntity newPetEntity = PetEntity.translateToPersistenceModel.apply(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = PetEntity.translateToDomainModel.apply(savedPetEntity);
		return savedPet;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Pet update(Pet updatedPet) {
		PetEntity petEntity = petEntityRepository.findById(updatedPet.getId());
		petEntity.setName(updatedPet.getName());
		petEntity.setBirthDate(updatedPet.getBirthDate());
		petEntity.setTypeId(updatedPet.getType().getId());

		PetEntity savedPetEntity = petEntityRepository.save(petEntity);
		Pet savedPet = PetEntity.translateToDomainModel.apply(savedPetEntity);
		return savedPet;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Pet findById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findById(petId);
		Pet pet = PetEntity.translateToDomainModel.apply(petEntity);
		return pet;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public Pet findWithVisitsById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(petId);
		Pet pet = PetEntity.translateToDomainModel.apply(petEntity);
		return pet;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId).stream()
			.map(PetEntity.translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll().stream()
			.map(PetTypeEntity.translateToDomainModel)
			.collect(Collectors.toList());
	}

}
