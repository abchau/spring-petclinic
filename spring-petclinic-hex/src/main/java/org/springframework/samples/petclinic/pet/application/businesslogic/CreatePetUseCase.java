package org.springframework.samples.petclinic.pet.application.businesslogic;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.drivenport.SavePetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.CreatePetPort;
import org.springframework.stereotype.Component;

/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Component
class CreatePetUseCase implements CreatePetPort {

	private final SavePetPort savePetPort;

	@Autowired
	public CreatePetUseCase(SavePetPort savePetPort) {
		this.savePetPort = savePetPort;
	}

	@Override
	public Pet createPet(Pet pet) {
		return savePetPort.create(pet);
	}

}
