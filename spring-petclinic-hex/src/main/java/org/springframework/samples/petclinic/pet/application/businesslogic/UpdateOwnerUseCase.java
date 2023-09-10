package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.UpdateOwnerPort;
import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.stereotype.Component;

/**
 * Application Service
 *
 * @author github.com/abchau
 */
@Component
/*final*/ class UpdateOwnerUseCase implements UpdateOwnerPort {

	private final SaveOwnerPort saveOwnerPort;

	@Autowired
	public UpdateOwnerUseCase(SaveOwnerPort saveOwnerPort) {
		this.saveOwnerPort = saveOwnerPort;
	}

	@Override
	public Owner updateOwner(Owner owner) {
		return saveOwnerPort.update(owner);
	}

}
