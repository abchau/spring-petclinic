package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.PetType;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadPetPort;
import org.springframework.samples.petclinic.pet.application.drivenport.SavePetPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryAdapter
@Service
class PetPersistenceAdapter implements LoadPetPort, SavePetPort {

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
		PetEntity newPetEntity = translateToPersistenceModel(newPet);
		PetEntity savedPetEntity = petEntityRepository.save(newPetEntity);
		Pet savedPet = translateToDomainModel(savedPetEntity);
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
		Pet savedPet = translateToDomainModel(savedPetEntity);
		return savedPet;
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findById(petId);
		Pet pet = translateToDomainModel(petEntity);
		return pet;
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findWithVisitsById(Integer petId) {
		PetEntity petEntity = petEntityRepository.findWithVisitsById(petId);
		Pet pet = translateToDomainModel(petEntity);
		return pet;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petEntityRepository.findAllByOwnerId(ownerId).stream()
			.map(PetPersistenceAdapter::translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PetType> findPetTypes() {
		return petTypeEntityRepository.findAll().stream()
			.map(PetPersistenceAdapter::translateToDomainModel)
			.collect(Collectors.toList());
	}

	static Pet translateToDomainModel(PetEntity petEntity) {
		Pet pet = new Pet();
		pet.setId(petEntity.getId());
		pet.setOwnerId(petEntity.getOwnerId());
		pet.setName(petEntity.getName());
		pet.setBirthDate(petEntity.getBirthDate());
		if (petEntity.getType() != null) {
			pet.setType(translateToDomainModel(petEntity.getType()));
		}
		if (petEntity.getVisits() != null) {
			pet.setVisits(petEntity.getVisits().stream()
				.map(VisitPersistenceAdapter::translateToDomainModel)
				.collect(Collectors.toList()));
		}

		return pet;
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

	static PetType translateToDomainModel(PetTypeEntity petTypeEntity) {
		PetType petType = new PetType();
		petType.setId(petTypeEntity.getId());
		petType.setName(petTypeEntity.getName());

		return petType;
	}
}
