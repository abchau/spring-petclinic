package org.springframework.samples.petclinic.pet.application.drivingport;

import java.util.List;

import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetType;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface ShowPetPort {

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public Pet findById(Integer id);

	public Pet findWithVisitsById(Integer id);

	public List<PetType> findPetTypes();

}
