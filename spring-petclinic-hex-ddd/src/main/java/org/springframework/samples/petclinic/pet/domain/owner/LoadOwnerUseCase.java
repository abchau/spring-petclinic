package org.springframework.samples.petclinic.pet.domain.owner;

import org.springframework.samples.petclinic.pet.domain.owner.Owner.PaginatedOwner;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface LoadOwnerUseCase {

	public PaginatedOwner findByLastName(String lastname, int page);

	public Owner findById(Integer ownerId);

	public Owner findWithPetsById(Integer ownerId);

}
