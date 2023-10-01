package org.springframework.samples.petclinic.pet.application.businesslogic;

import java.util.List;

import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.application.Pet;
import org.springframework.samples.petclinic.pet.application.PetType;
import org.springframework.samples.petclinic.pet.application.drivenport.LoadPetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowPetPort;
import org.springframework.stereotype.Component;

/**
 * @author github.com/abchau
 */
@PrimaryAdapter
@Component
class ShowPetUseCase implements ShowPetPort {

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
