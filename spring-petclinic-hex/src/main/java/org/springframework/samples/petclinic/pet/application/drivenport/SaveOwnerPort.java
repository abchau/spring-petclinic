package org.springframework.samples.petclinic.pet.application.drivenport;

import org.springframework.samples.petclinic.pet.domain.Owner;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface SaveOwnerPort {

	public Owner create(Owner owner);

	public Owner update(Owner owner);

}
