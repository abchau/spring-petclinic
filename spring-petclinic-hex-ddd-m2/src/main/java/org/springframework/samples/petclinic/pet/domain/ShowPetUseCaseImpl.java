package org.springframework.samples.petclinic.pet.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
