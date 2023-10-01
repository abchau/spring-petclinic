package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.Visit;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveVisitPort;
import org.springframework.samples.petclinic.pet.application.drivingport.AddVisitPort;
import org.springframework.stereotype.Component;

/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Component
class ArchitectureTests implements AddVisitPort {

	private final SaveVisitPort saveVisitPort;

	@Autowired
	public ArchitectureTests(SaveVisitPort saveVisitPort) {
		this.saveVisitPort = saveVisitPort;
	}

	@Override
	public Visit addVisit(Visit visit) {
		return saveVisitPort.create(visit);
	}

}
