package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.domain.pet.Pet;
import org.springframework.samples.petclinic.pet.domain.pet.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.pet.LoadPetUseCase;
import org.springframework.samples.petclinic.pet.domain.pet.SavePetUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It can be a local transaction or RPC to other services, loud services, etc
 */
@Service
public class PetPersistenceAdapter implements LoadPetUseCase, SavePetUseCase {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

	@Autowired
	public PetPersistenceAdapter(PetEntityRepository petEntityRepository,
			PetTypeEntityRepository petTypeEntityRepository) {
		this.petEntityRepository = petEntityRepository;
		this.petTypeEntityRepository = petTypeEntityRepository;
	}

	@Override
	@Transactional
	public Pet create(Pet newPet) {
		PetEntity newPetEntity = PetEntityTranslator.toPersistenceModel(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = PetEntityTranslator.toDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	@Transactional
	public Pet update(Pet updatedPet) {
		PetEntity updatedPetEntity = PetEntityTranslator.toPersistenceModel(updatedPet);
		PetEntity savedPetEntity = petEntityRepository.save(updatedPetEntity);
		Pet savedPet = PetEntityTranslator.toDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findById(petId);
		Pet pet = PetEntityTranslator.toDomainModel(petEntity);
		return pet;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Pet findWithVisitsById(Integer id) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(id);
		Pet pet = PetEntityTranslator.toDomainModel(petEntity);
		return pet;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId)
			.stream()
			.map(PetEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll()
			.stream()
			.map(PetTypeEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

}
