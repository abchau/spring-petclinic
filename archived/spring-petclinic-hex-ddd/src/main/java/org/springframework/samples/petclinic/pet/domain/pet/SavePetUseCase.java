package org.springframework.samples.petclinic.pet.domain.pet;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface SavePetUseCase {

	public Pet create(Pet pet);

	public Pet update(Pet pet);

}
