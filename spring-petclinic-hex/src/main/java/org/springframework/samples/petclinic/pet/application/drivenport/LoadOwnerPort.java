package org.springframework.samples.petclinic.pet.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.pet.application.Owner;
import org.springframework.samples.petclinic.pet.application.Owner.PaginatedOwner;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface LoadOwnerPort {

	public PaginatedOwner findByLastName(String lastname, int page);

	public Owner findById(Integer ownerId);

	public Owner findWithPetsById(Integer ownerId);

}
