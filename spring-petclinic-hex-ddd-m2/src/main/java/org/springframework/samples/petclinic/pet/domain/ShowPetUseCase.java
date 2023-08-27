package org.springframework.samples.petclinic.pet.domain;

import java.util.List;

// API
public interface ShowPetUseCase {

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public Pet findById(Integer id);
	
	public Pet findWithVisitsById(Integer id);
	
	public List<PetType> findPetTypes();

}
