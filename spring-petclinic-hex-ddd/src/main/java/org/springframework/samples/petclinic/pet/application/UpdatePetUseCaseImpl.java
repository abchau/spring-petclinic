package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetRepository;
import org.springframework.samples.petclinic.pet.domain.UpdatePetUseCase;
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
