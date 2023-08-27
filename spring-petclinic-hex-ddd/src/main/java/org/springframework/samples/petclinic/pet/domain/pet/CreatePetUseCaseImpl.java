package org.springframework.samples.petclinic.pet.domain.pet;

import org.springframework.beans.factory.annotation.Autowired;
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
