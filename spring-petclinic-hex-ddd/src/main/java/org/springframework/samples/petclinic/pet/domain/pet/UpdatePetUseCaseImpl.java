package org.springframework.samples.petclinic.pet.domain.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// API
// Domain Service
@Component
class UpdatePetUseCaseImpl implements UpdatePetUseCase {

	private final SavePetUseCase savePetUseCase;

	@Autowired
	public UpdatePetUseCaseImpl(SavePetUseCase savePetUseCase) {
		this.savePetUseCase = savePetUseCase;
	}

	@Override
	public Pet updatePet(Pet pet) {
		return savePetUseCase.update(pet);
	}

}
