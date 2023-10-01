package org.springframework.samples.petclinic.pet.application.businesslogic;

import java.util.List;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.Visit;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadVisitPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowVisitPort;
import org.springframework.stereotype.Component;

/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Component
class ShowVisitUseCase implements ShowVisitPort {

	private final LoadVisitPort loadVisitPort;

	@Autowired
	public ShowVisitUseCase(LoadVisitPort loadVisitPort) {
		this.loadVisitPort = loadVisitPort;
	}

	@Override
	public List<Visit> findVisits(Integer petId) {
		return loadVisitPort.findAllByPetId(petId);
	}

}
