package org.springframework.samples.petclinic.owner.infrastructure;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.pet.infrastructure.PetEntityTranslator;

/**
 * Technology-specific anti-corruption layer
 *
 * @author github.com/abchau
 */
public class OwnerEntityTranslator {

	public static Owner toDomainModel(OwnerEntity ownerEntity) {
		Owner owner = new Owner();
		owner.setId(ownerEntity.getId());
		owner.setFirstName(ownerEntity.getFirstName());
		owner.setLastName(ownerEntity.getLastName());
		owner.setAddress(ownerEntity.getAddress());
		owner.setCity(ownerEntity.getCity());
		owner.setTelephone(ownerEntity.getTelephone());
		owner.setPets(
				ownerEntity.getPets().stream().map(PetEntityTranslator::toDomainModel).collect(Collectors.toList()));

		return owner;
	}

	public static OwnerEntity toPersistenceModel(Owner owner) {
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
