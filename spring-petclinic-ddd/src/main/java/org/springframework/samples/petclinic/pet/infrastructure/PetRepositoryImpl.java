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
 * Technology-specific provider of a DDD Repository
 *
 * @author github.com/abchau
 */
@Service
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
		PetEntity newPetEntity = PetRepositoryImpl.translateToPersistenceModel(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = PetRepositoryImpl.translateToDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	public Pet update(Pet updatedPet) {
		PetEntity updatedPetEntity = PetRepositoryImpl.translateToPersistenceModel(updatedPet);
		PetEntity savedPetEntity = petEntityRepository.save(updatedPetEntity);
		Pet savedPet = PetRepositoryImpl.translateToDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	public Pet findById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findById(petId);
		Pet pet = PetRepositoryImpl.translateToDomainModel(petEntity);
		return pet;
	}

	@Override
	public Pet findWithVisitsById(Integer id) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(id);
		Pet pet = PetRepositoryImpl.translateToDomainModel(petEntity);
		return pet;
	}

	@Override
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId)
			.stream()
			.map(PetRepositoryImpl::translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll()
			.stream()
			.map(PetRepositoryImpl::translateToDomainModel)
			.collect(Collectors.toList());
	}

	static Pet translateToDomainModel(PetEntity petEntity) {
		Pet pet = new Pet();
		pet.setId(petEntity.getId());
		pet.setOwnerId(petEntity.getOwnerId());
		pet.setName(petEntity.getName());
		pet.setBirthDate(petEntity.getBirthDate());
		if (petEntity.getType() != null) {
			pet.setType(PetRepositoryImpl.translateToDomainModel(petEntity.getType()));
		}
		if (petEntity.getVisits() != null) {
			pet.setVisits(petEntity.getVisits()
				.stream()
				.map(VisitRepositoryImpl::translateToDomainModel)
				.collect(Collectors.toList()));
		}

		return pet;
	}

	static PetType translateToDomainModel(PetTypeEntity petTypeEntity) {
		PetType petType = new PetType();
		petType.setId(petTypeEntity.getId());
		petType.setName(petTypeEntity.getName());

		return petType;
	}

 	static PetEntity translateToPersistenceModel(Pet pet) {
		PetEntity petEntity = new PetEntity();
		petEntity.setId(pet.getId());
		petEntity.setOwnerId(pet.getOwnerId());
		petEntity.setName(pet.getName());
		petEntity.setBirthDate(pet.getBirthDate());
		petEntity.setTypeId(pet.getType().getId());

		return petEntity;
	}

}
