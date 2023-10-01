package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.drivenport.SavePetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.UpdatePetPort;
import org.springframework.stereotype.Component;

/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Component
class UpdatePetUseCase implements UpdatePetPort {

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
