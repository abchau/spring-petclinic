package org.springframework.samples.petclinic.pet.application.drivingport;


import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.pet.application.Owner;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface UpdateOwnerPort {

	public Owner updateOwner(Owner owner);

}
