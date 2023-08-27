package org.springframework.samples.petclinic.pet.application.drivenport;

import org.springframework.samples.petclinic.pet.domain.Pet;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface SavePetUseCase {

	public Pet create(Pet pet);

	public Pet update(Pet pet);

}
