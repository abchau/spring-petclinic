package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveVisitPort;
import org.springframework.samples.petclinic.pet.application.drivingport.AddVisitPort;
import org.springframework.samples.petclinic.pet.domain.Visit;
import org.springframework.stereotype.Component;

/**
 * Application Service
 *
 * @author github.com/abchau
 */
@Component
/*final*/ class AddVisitUseCase implements AddVisitPort {

	private final SaveVisitPort saveVisitPort;

	@Autowired
	public AddVisitUseCase(SaveVisitPort saveVisitPort) {
		this.saveVisitPort = saveVisitPort;
	}

	@Override
	public Visit addVisit(Visit visit) {
		return saveVisitPort.create(visit);
	}

}
