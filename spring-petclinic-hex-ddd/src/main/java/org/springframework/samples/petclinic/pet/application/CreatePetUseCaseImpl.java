package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.CreatePetUseCase;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetRepository;
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
