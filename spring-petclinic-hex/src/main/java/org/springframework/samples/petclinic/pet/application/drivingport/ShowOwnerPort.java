package org.springframework.samples.petclinic.pet.application.drivingport;

import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface ShowOwnerPort {

	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page);

	public Owner findById(Integer id);

	public Owner findWithPetsById(Integer id);

}
