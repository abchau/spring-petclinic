package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.application.drivenport.LoadPetPort;
import org.springframework.samples.petclinic.pet.application.drivenport.SavePetPort;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@Service
/*final*/ class PetPersistenceAdapter implements LoadPetPort, SavePetPort {

	private final PetEntityRepository petEntityRepository;

	private final PetTypeEntityRepository petTypeEntityRepository;

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
		PetEntity petEntity = petEntityRepository.findById(updatedPet.getId());
		petEntity.setName(updatedPet.getName());
		petEntity.setBirthDate(updatedPet.getBirthDate());
		petEntity.setTypeId(updatedPet.getType().getId());

		PetEntity savedPetEntity = petEntityRepository.save(petEntity);
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
	public Pet findWithVisitsById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(petId);
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
