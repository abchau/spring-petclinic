package org.springframework.samples.petclinic.pet.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.pet.application.Owner;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface SaveOwnerPort {

	public Owner create(Owner owner);

	public Owner update(Owner owner);

}
