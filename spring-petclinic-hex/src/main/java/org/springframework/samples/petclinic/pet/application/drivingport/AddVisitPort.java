package org.springframework.samples.petclinic.pet.application.drivingport;


import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.pet.application.Visit;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface AddVisitPort {

	public Visit addVisit(Visit visit);

}
