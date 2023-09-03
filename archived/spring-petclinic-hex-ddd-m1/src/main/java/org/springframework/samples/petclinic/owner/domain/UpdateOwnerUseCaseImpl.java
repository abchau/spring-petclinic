package org.springframework.samples.petclinic.owner.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Please note that it is package scope
 *
 * @author github.com/abchau
 */
@Component
class UpdateOwnerUseCaseImpl implements UpdateOwnerUseCase {

	private final OwnerRepository ownerRepository;

	@Autowired
	public UpdateOwnerUseCaseImpl(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public Owner updateOwner(Owner owner) {
		return ownerRepository.update(owner);
	}

}
