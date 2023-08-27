package org.springframework.samples.petclinic.pet.application.drivingport;

import org.springframework.samples.petclinic.pet.domain.Pet;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface UpdatePetPort {

	public Pet updatePet(Pet pet);

}
