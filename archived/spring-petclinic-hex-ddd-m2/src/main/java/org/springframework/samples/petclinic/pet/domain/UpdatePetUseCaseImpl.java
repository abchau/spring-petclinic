package org.springframework.samples.petclinic.pet.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// API
// Domain Service
@Component
class UpdatePetUseCaseImpl implements UpdatePetUseCase {

	private final PetRepository petRepository;

	@Autowired
	public UpdatePetUseCaseImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public Pet updatePet(Pet pet) {
		return petRepository.update(pet);
	}

}
