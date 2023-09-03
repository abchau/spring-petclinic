package org.springframework.samples.petclinic.pet.domain;

import java.util.List;

// SPI
public interface PetRepository {

	public Pet create(Pet pet);

	public Pet update(Pet pet);

	public Pet findById(Integer petId);

	public Pet findWithVisitsById(Integer petId);
	
	public List<Pet> findAllByOwnerId(Integer ownerId);

	public List<PetType> findPetTypes();

}
