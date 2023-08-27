package org.springframework.samples.petclinic.pet.application.drivenport;

import java.util.List;

import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetType;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface LoadPetPort {

	public Pet findById(Integer petId);

	public Pet findWithVisitsById(Integer petId);

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public List<PetType> findPetTypes();

}
