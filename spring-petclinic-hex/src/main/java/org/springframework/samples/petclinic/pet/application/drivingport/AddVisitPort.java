package org.springframework.samples.petclinic.pet.application.drivingport;

import org.springframework.samples.petclinic.pet.domain.Visit;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface AddVisitPort {

	public Visit addVisit(Visit visit);

}
