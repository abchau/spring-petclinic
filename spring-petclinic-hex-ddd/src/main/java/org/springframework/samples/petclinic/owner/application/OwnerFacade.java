package org.springframework.samples.petclinic.owner.application;

// no source code dependency on infrastructure
import org.springframework.samples.petclinic.owner.domain.CreateOwnerUseCase;
import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.owner.domain.ShowOwnerUseCase;
import org.springframework.samples.petclinic.owner.domain.UpdateOwnerUseCase;
import org.springframework.samples.petclinic.owner.domain.OwnerRepository.PaginatedOwner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Application "interfaces" of the domain for the outside world to consume. Also the only
 * public class in application layer.
 * <p>
 * Not necessary but can also choose to create an application layer Dto to hide the actual
 * domain data model from the outside world
 *
 * @author github.com/abchau
 */
@Service
public class OwnerFacade {

	private final ShowOwnerUseCase showOwnerUseCase;

	private final CreateOwnerUseCase createOwnerUseCase;

	private final UpdateOwnerUseCase updateOwnerUseCase;

	@Autowired
	public OwnerFacade(ShowOwnerUseCase showOwnerUseCase, CreateOwnerUseCase createOwnerUseCase,
			UpdateOwnerUseCase updateOwnerUseCase) {
		this.showOwnerUseCase = showOwnerUseCase;
		this.createOwnerUseCase = createOwnerUseCase;
		this.updateOwnerUseCase = updateOwnerUseCase;
	}

	public Owner createOwner(CreateOwnerCommand command) {
		Owner newOwner = new Owner();
		newOwner.setFirstName(command.getFirstName());
		newOwner.setLastName(command.getLastName());
		newOwner.setAddress(command.getAddress());
		newOwner.setCity(command.getCity());
		newOwner.setTelephone(command.getTelephone());

		return createOwnerUseCase.createOwner(newOwner);
	}

	public Owner updateOwner(UpdateOwnerCommand command) {
		Owner changedOwner = new Owner();
		changedOwner.setId(command.getId());
		changedOwner.setFirstName(command.getFirstName());
		changedOwner.setLastName(command.getLastName());
		changedOwner.setAddress(command.getAddress());
		changedOwner.setCity(command.getCity());
		changedOwner.setTelephone(command.getTelephone());

		return updateOwnerUseCase.updateOwner(changedOwner);
	}

	public PaginatedOwner findPaginatedForOwnersLastName(int page, String lastname) {
		return showOwnerUseCase.findPaginatedForOwnersLastName(page, lastname);
	}

	public Owner findById(Integer id) {
		return showOwnerUseCase.findById(id);
	}

	public Owner findWithPetsById(Integer id) {
		return showOwnerUseCase.findWithPetsById(id);
	}
	
	// input requirement could change from facade to facade. it is unique to this facade
	// hence public static inner class
	public static class CreateOwnerCommand {

		private String firstName;

		private String lastName;

		private String address;

		private String city;

		private String telephone;

		public String getFirstName() {
			return this.firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getAddress() {
			return this.address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return this.city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getTelephone() {
			return this.telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

	}

	// input requirement could change from facade to facade. it is unique to this facade
	// hence public static inner class
	public static class UpdateOwnerCommand extends CreateOwnerCommand {

		private Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

	}

}
