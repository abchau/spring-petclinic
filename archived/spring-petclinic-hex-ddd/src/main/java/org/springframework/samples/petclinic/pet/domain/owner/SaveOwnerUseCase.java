package org.springframework.samples.petclinic.pet.domain.owner;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface SaveOwnerUseCase {

	public Owner create(Owner owner);

	public Owner update(Owner owner);

}
