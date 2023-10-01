package org.springframework.samples.petclinic.pet.application.drivingport;

import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.pet.application.Owner;
import org.springframework.samples.petclinic.pet.application.Owner.PaginatedOwner;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface ShowOwnerPort {

	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page);

	public Owner findById(Integer id);

	public Owner findWithPetsById(Integer id);

}
