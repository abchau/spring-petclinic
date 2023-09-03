package org.springframework.samples.petclinic.pet.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.pet.LoadPetUseCase;
import org.springframework.samples.petclinic.pet.domain.pet.Pet;
import org.springframework.samples.petclinic.pet.domain.pet.PetType;
import org.springframework.samples.petclinic.pet.domain.pet.ShowPetUseCase;
import org.springframework.stereotype.Component;

// API
// Domain Service
@Component
class ShowPetUseCaseImpl implements ShowPetUseCase {

	private final LoadPetUseCase loadPetUseCase;

	@Autowired
	public ShowPetUseCaseImpl(LoadPetUseCase loadPetUseCase) {
		this.loadPetUseCase = loadPetUseCase;
	}

	@Override
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return loadPetUseCase.findAllByOwnerId(ownerId);
	}

	@Override
	public Pet findById(Integer id) {
		return loadPetUseCase.findById(id);
	}

	@Override
	public Pet findWithVisitsById(Integer id) {
		return loadPetUseCase.findWithVisitsById(id);
	}

	@Override
	public List<PetType> findPetTypes() {
		return loadPetUseCase.findPetTypes();
	}

}
