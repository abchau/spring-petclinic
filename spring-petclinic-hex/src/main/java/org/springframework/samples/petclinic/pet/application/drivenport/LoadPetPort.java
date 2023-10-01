package org.springframework.samples.petclinic.pet.application.drivenport;

import java.util.List;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.PetType;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface LoadPetPort {

	public Pet findById(Integer petId);

	public Pet findWithVisitsById(Integer petId);

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public List<PetType> findPetTypes();

}
