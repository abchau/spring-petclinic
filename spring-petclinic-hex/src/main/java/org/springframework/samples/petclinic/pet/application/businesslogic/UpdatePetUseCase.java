package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.drivenport.SavePetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.UpdatePetPort;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.stereotype.Component;

/**
 * Application Service
 *
 * @author github.com/abchau
 */
@Component
/*final*/ class UpdatePetUseCase implements UpdatePetPort {

	private final SavePetPort savePetPort;

	@Autowired
	public UpdatePetUseCase(SavePetPort savePetPort) {
		this.savePetPort = savePetPort;
	}

	@Override
	public Pet updatePet(Pet pet) {
		return savePetPort.update(pet);
	}

}
