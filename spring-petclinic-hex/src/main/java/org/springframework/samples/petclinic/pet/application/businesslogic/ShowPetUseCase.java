package org.springframework.samples.petclinic.pet.application.businesslogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadPetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowPetPort;
import org.springframework.samples.petclinic.pet.domain.Pet;
import org.springframework.samples.petclinic.pet.domain.PetType;
import org.springframework.stereotype.Component;

/**
 * Application Service
 *
 * @author github.com/abchau
 */
@Component
/*final*/ class ShowPetUseCase implements ShowPetPort {

	private final LoadPetPort loadPetPort;

	@Autowired
	public ShowPetUseCase(LoadPetPort loadPetPort) {
		this.loadPetPort = loadPetPort;
	}

	@Override
	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return loadPetPort.findAllByOwnerId(ownerId);
	}

	@Override
	public Pet findById(Integer id) {
		return loadPetPort.findById(id);
	}

	@Override
	public Pet findWithVisitsById(Integer id) {
		return loadPetPort.findWithVisitsById(id);
	}

	@Override
	public List<PetType> findPetTypes() {
		return loadPetPort.findPetTypes();
	}

}
