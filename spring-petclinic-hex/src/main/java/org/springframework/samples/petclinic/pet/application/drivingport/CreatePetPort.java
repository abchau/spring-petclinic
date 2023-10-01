package org.springframework.samples.petclinic.pet.application.drivingport;


import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.pet.application.Pet;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface CreatePetPort {

	public Pet createPet(Pet pet);

}
