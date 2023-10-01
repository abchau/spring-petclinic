package org.springframework.samples.petclinic.pet.application.drivingport;

import java.util.List;

import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.PetType;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface ShowPetPort {

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public Pet findById(Integer id);

	public Pet findWithVisitsById(Integer id);

	public List<PetType> findPetTypes();

}
