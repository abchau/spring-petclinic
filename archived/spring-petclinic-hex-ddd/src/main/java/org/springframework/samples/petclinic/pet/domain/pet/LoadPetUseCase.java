package org.springframework.samples.petclinic.pet.domain.pet;

import java.util.List;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface LoadPetUseCase {

	public Pet findById(Integer petId);

	public Pet findWithVisitsById(Integer petId);

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public List<PetType> findPetTypes();

}
