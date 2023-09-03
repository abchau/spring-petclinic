package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.pet.CreatePetUseCase;
import org.springframework.samples.petclinic.pet.domain.pet.Pet;
import org.springframework.samples.petclinic.pet.domain.pet.SavePetUseCase;
import org.springframework.stereotype.Component;

// API
// Domain Service
@Component
class CreatePetUseCaseImpl implements CreatePetUseCase {

	private final SavePetUseCase savePetUseCase;

	@Autowired
	public CreatePetUseCaseImpl(SavePetUseCase savePetUseCase) {
		this.savePetUseCase = savePetUseCase;
	}

	@Override
	public Pet createPet(Pet pet) {
		return savePetUseCase.create(pet);
	}

}
