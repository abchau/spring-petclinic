package org.springframework.samples.petclinic.pet.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.pet.application.Pet;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface SavePetPort {

	public Pet create(Pet pet);

	public Pet update(Pet pet);

}
