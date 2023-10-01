package org.springframework.samples.petclinic.pet.application.drivingport;

import java.util.List;

import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.pet.application.Visit;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface ShowVisitPort {

	public List<Visit> findVisits(Integer petId);

}
