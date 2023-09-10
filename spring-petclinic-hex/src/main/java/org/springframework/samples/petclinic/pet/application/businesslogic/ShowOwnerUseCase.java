package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowOwnerPort;
import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;
import org.springframework.stereotype.Component;

/**
 * Application Service
 *
 * @author github.com/abchau
 */
@Component
/*final*/ class ShowOwnerUseCase implements ShowOwnerPort {

	private final LoadOwnerPort loadOwnerPort;

	@Autowired
	public ShowOwnerUseCase(LoadOwnerPort loadOwnerPort) {
		this.loadOwnerPort = loadOwnerPort;
	}

	@Override
	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page) {
		return loadOwnerPort.findByLastName(lastname, page);
	}

	@Override
	public Owner findById(Integer id) {
		return loadOwnerPort.findById(id);
	}

	@Override
	public Owner findWithPetsById(Integer id) {
		return loadOwnerPort.findWithPetsById(id);
	}

}
