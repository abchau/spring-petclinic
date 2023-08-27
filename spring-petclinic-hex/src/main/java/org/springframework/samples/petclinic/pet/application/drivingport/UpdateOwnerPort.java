package org.springframework.samples.petclinic.pet.application.drivingport;

import org.springframework.samples.petclinic.pet.domain.Owner;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface UpdateOwnerPort {

	public Owner updateOwner(Owner owner);

}
