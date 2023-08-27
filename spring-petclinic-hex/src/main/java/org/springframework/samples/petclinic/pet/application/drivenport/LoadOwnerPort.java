package org.springframework.samples.petclinic.pet.application.drivenport;

import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface LoadOwnerPort {

	public PaginatedOwner findByLastName(String lastname, int page);

	public Owner findById(Integer ownerId);

	public Owner findWithPetsById(Integer ownerId);

}
