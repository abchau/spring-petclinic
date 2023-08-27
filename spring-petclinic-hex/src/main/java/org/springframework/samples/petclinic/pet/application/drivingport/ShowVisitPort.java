package org.springframework.samples.petclinic.pet.application.drivingport;

import java.util.List;

import org.springframework.samples.petclinic.pet.domain.Visit;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface ShowVisitPort {

	public List<Visit> findVisits(Integer petId);

}
