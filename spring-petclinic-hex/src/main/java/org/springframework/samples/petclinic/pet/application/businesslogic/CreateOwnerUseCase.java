package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.CreateOwnerPort;
import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.stereotype.Component;

/**
 * Application Service
 *
 * @author github.com/abchau
 */
@Component
class CreateOwnerUseCase implements CreateOwnerPort {

	private final SaveOwnerPort saveOwnerPort;

	@Autowired
	public CreateOwnerUseCase(SaveOwnerPort saveOwnerPort) {
		this.saveOwnerPort = saveOwnerPort;
	}

	@Override
	public Owner createOwner(Owner owner) {
		return saveOwnerPort.create(owner);
	}

}
