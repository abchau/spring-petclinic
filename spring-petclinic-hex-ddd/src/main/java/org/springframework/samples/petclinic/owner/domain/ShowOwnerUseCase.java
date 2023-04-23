package org.springframework.samples.petclinic.owner.domain;

import org.springframework.samples.petclinic.owner.domain.OwnerRepository.PaginatedOwner;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface ShowOwnerUseCase {

	public PaginatedOwner findPaginatedForOwnersLastName(int page, String lastname);
	
	public Owner findById(Integer id);

	public Owner findWithPetsById(Integer id);

}
