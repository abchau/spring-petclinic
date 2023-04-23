package org.springframework.samples.petclinic.pet.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetRepository;
import org.springframework.samples.petclinic.pet.domain.PetType;
import org.springframework.samples.petclinic.pet.domain.ShowPetUseCase;
import org.springframework.stereotype.Component;

// API
// Domain Service
@Component
class ShowPetUseCaseImpl implements ShowPetUseCase {

	private final PetRepository petRepository;

	@Autowired
	public ShowPetUseCaseImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return petRepository.findAllByOwnerId(ownerId);
	}

	@Override
	public Pet findById(Integer id) {
		return petRepository.findById(id);
	}

	@Override
	public Pet findWithVisitsById(Integer id) {
		return petRepository.findWithVisitsById(id);
	}

	@Override
	public List<PetType> findPetTypes() {
		return petRepository.findPetTypes();
	}

}
