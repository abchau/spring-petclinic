package org.springframework.samples.petclinic.owner.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.domain.OwnerRepository.PaginatedOwner;
import org.springframework.stereotype.Component;

/**
 * Please note that it is package scope
 *
 * @author github.com/abchau
 */
@Component
class ShowOwnerUseCaseImpl implements ShowOwnerUseCase {

	private final OwnerRepository ownerRepository;

	@Autowired
	public ShowOwnerUseCaseImpl(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public PaginatedOwner findPaginatedForOwnersLastName(String lastname, int page) {
		return ownerRepository.findByLastName(lastname, page);
	}

	@Override
	public Owner findById(Integer id) {
		return ownerRepository.findById(id);
	}

	@Override
	public Owner findWithPetsById(Integer id) {
		return ownerRepository.findWithPetsById(id);
	}

}
