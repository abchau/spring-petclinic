package org.springframework.samples.petclinic.pet.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// API
// Domain Service
@Component
class CreatePetUseCaseImpl implements CreatePetUseCase {

	private final PetRepository petRepository;

	@Autowired
	public CreatePetUseCaseImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public Pet createPet(Pet pet) {
		return petRepository.create(pet);
	}

}
