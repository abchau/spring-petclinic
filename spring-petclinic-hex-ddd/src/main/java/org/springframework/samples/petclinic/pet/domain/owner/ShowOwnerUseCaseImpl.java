package org.springframework.samples.petclinic.pet.domain.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.owner.Owner.PaginatedOwner;
import org.springframework.stereotype.Component;

/**
 * Domain Service
 * @author github.com/abchau
 */
@Component
class ShowOwnerUseCaseImpl implements ShowOwnerUseCase {

	private final LoadOwnerUseCase loadOwnerUseCase;

	@Autowired
	public ShowOwnerUseCaseImpl(LoadOwnerUseCase loadOwnerUseCase) {
		this.loadOwnerUseCase = loadOwnerUseCase;
	}

	@Override
	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page) {
		return loadOwnerUseCase.findByLastName(lastname, page);
	}

	@Override
	public Owner findById(Integer id) {
		return loadOwnerUseCase.findById(id);
	}

	@Override
	public Owner findWithPetsById(Integer id) {
		return loadOwnerUseCase.findWithPetsById(id);
	}

}
