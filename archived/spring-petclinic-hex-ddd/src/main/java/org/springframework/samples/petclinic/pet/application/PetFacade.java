package org.springframework.samples.petclinic.pet.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.pet.CreatePetUseCase;
import org.springframework.samples.petclinic.pet.domain.pet.Pet;
import org.springframework.samples.petclinic.pet.domain.pet.PetType;
import org.springframework.samples.petclinic.pet.domain.pet.ShowPetUseCase;
import org.springframework.samples.petclinic.pet.domain.pet.UpdatePetUseCase;
import org.springframework.stereotype.Service;

// Application Service
@Service
public class PetFacade {

	private final ShowPetUseCase showPetUseCase;

	private final CreatePetUseCase createPetUseCase;

	private final UpdatePetUseCase updatePetUseCase;

	@Autowired
	public PetFacade(ShowPetUseCase showPetUseCase, CreatePetUseCase createPetUseCase,
			UpdatePetUseCase updatePetUseCase) {
		this.showPetUseCase = showPetUseCase;
		this.createPetUseCase = createPetUseCase;
		this.updatePetUseCase = updatePetUseCase;
	}

	public Pet createPet(CreatePetCommand command) {
		Pet newPet = new Pet();
		newPet.setOwnerId(command.getOwnerId());
		newPet.setName(command.getName());
		newPet.setBirthDate(command.getBirthDate());
		newPet.setType(command.getType());

		return createPetUseCase.createPet(newPet);
	}

	public Pet updatePet(UpdatePetCommand command) {
		Pet changedPet = new Pet();
		changedPet.setId(command.getId());
		changedPet.setOwnerId(command.getOwnerId());
		changedPet.setName(command.getName());
		changedPet.setBirthDate(command.getBirthDate());
		changedPet.setType(command.getType());

		return updatePetUseCase.updatePet(changedPet);
	}

	public List<Pet> findAllByOwnerId(Integer ownerId) {
		return showPetUseCase.findAllByOwnerId(ownerId);
	}

	public Pet findById(Integer id) {
		return showPetUseCase.findById(id);
	}

	public Pet findWithVisitsById(Integer id) {
		return showPetUseCase.findWithVisitsById(id);
	}

	public List<PetType> findPetTypes() {
		return showPetUseCase.findPetTypes();
	}

	// input requirement could change from facade to facade. it is unique to this facade
	// hence public static inner class
	public static class CreatePetCommand {

		private Integer ownerId;

		private String name;

		private LocalDate birthDate;

		private PetType type;

		public Integer getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(Integer ownerId) {
			this.ownerId = ownerId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public LocalDate getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(LocalDate birthDate) {
			this.birthDate = birthDate;
		}

		public PetType getType() {
			return type;
		}

		public void setType(PetType type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "CreatePetCommand [ownerId=" + ownerId + ", name=" + name + ", birthDate=" + birthDate + ", type="
					+ type + "]";
		}

	}

	// input requirement could change from facade to facade. it is unique to this facade
	// hence public static inner class
	public static class UpdatePetCommand extends CreatePetCommand {

		private Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "UpdatePetCommand [super=" + super.toString() + ", id=" + id + "]";
		}

	}

}
