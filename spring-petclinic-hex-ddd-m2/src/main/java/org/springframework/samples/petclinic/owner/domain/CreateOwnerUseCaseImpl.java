package org.springframework.samples.petclinic.owner.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Please note that it is package scope
 *
 * @author github.com/abchau
 */
@Component
class CreateOwnerUseCaseImpl implements CreateOwnerUseCase {

	private final OwnerRepository ownerRepository;

	@Autowired
	public CreateOwnerUseCaseImpl(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public Owner createOwner(Owner owner) {
		return ownerRepository.create(owner);
	}

}
