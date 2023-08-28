package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.owner.CreateOwnerUseCase;
import org.springframework.samples.petclinic.pet.domain.owner.Owner;
import org.springframework.samples.petclinic.pet.domain.owner.SaveOwnerUseCase;
import org.springframework.stereotype.Component;

/**
 * Domain Service
 * @author github.com/abchau
 */
@Component
class CreateOwnerUseCaseImpl implements CreateOwnerUseCase {

	private final SaveOwnerUseCase saveOwnerUseCase;

	@Autowired
	public CreateOwnerUseCaseImpl(SaveOwnerUseCase saveOwnerUseCase) {
		this.saveOwnerUseCase = saveOwnerUseCase;
	}

	@Override
	public Owner createOwner(Owner owner) {
		return saveOwnerUseCase.create(owner);
	}

}
