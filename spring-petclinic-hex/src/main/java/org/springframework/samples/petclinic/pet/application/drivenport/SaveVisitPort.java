package org.springframework.samples.petclinic.pet.application.drivenport;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.pet.application.Visit;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface SaveVisitPort{

	public Visit create(Visit visit);

}
