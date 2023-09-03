package org.springframework.samples.petclinic.pet.domain.owner;

import org.springframework.samples.petclinic.pet.domain.owner.Owner.PaginatedOwner;

/**
 * Domain Service
 * @author github.com/abchau
 */
public interface ShowOwnerUseCase {

	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page);
	
	public Owner findById(Integer id);

	public Owner findWithPetsById(Integer id);

}
