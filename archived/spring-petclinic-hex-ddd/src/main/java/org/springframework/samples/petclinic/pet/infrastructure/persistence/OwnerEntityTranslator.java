package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.domain.owner.Owner;

/**
 * Technology-specific translation
 *
 * @author github.com/abchau
 */
class OwnerEntityTranslator {

	static Owner toDomainModel(OwnerEntity ownerEntity) {
		Owner owner = new Owner();
		owner.setId(ownerEntity.getId());
		owner.setFirstName(ownerEntity.getFirstName());
		owner.setLastName(ownerEntity.getLastName());
		owner.setAddress(ownerEntity.getAddress());
		owner.setCity(ownerEntity.getCity());
		owner.setTelephone(ownerEntity.getTelephone());
		if (ownerEntity.getPets() != null) {
			owner.setPets(
				ownerEntity.getPets().stream().map(PetEntityTranslator::toDomainModel).collect(Collectors.toList()));
		}
		return owner;
	}

	static OwnerEntity toPersistenceModel(Owner owner) {
		OwnerEntity ownerEntity = new OwnerEntity();
		ownerEntity.setId(owner.getId());
		ownerEntity.setFirstName(owner.getFirstName());
		ownerEntity.setLastName(owner.getLastName());
		ownerEntity.setAddress(owner.getAddress());
		ownerEntity.setCity(owner.getCity());
		ownerEntity.setTelephone(owner.getTelephone());

		return ownerEntity;
	}

}
