package org.springframework.samples.petclinic.pet.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.owner.Owner;
import org.springframework.samples.petclinic.pet.domain.owner.SaveOwnerUseCase;
import org.springframework.samples.petclinic.pet.domain.owner.UpdateOwnerUseCase;
import org.springframework.stereotype.Component;

/**
 * Domain Service
 * @author github.com/abchau
 */
@Component
class UpdateOwnerUseCaseImpl implements UpdateOwnerUseCase {

	private final SaveOwnerUseCase saveOwnerUseCase;

	@Autowired
	public UpdateOwnerUseCaseImpl(SaveOwnerUseCase saveOwnerUseCase) {
		this.saveOwnerUseCase = saveOwnerUseCase;
	}

	@Override
	public Owner updateOwner(Owner owner) {
		return saveOwnerUseCase.update(owner);
	}

}
