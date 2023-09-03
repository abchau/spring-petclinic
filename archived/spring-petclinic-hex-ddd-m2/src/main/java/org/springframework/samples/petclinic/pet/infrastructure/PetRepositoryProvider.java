package org.springframework.samples.petclinic.pet.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetRepository;
import org.springframework.samples.petclinic.pet.domain.PetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It can be a local transaction or RPC to other services, loud services, etc
 */
@Service
public class PetRepositoryProvider implements PetRepository {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

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
	public Pet findWithVisitsById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(petId);
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
