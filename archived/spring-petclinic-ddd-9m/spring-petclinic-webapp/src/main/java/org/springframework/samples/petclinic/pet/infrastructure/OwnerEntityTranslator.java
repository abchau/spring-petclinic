/*
 * Copyright 2023- the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.pet.infrastructure;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.domain.Owner;

/**
 * Technology-specific anti-corruption layer
 *
 * @author github.com/abchau
 */
final class OwnerEntityTranslator {

	public static Owner toDomainModel(OwnerEntity ownerEntity) {
		Owner owner = new Owner();
		owner.setId(ownerEntity.getId());
		owner.setFirstName(ownerEntity.getFirstName());
		owner.setLastName(ownerEntity.getLastName());
		owner.setAddress(ownerEntity.getAddress());
		owner.setCity(ownerEntity.getCity());
		owner.setTelephone(ownerEntity.getTelephone());
		if (ownerEntity.getPets() != null) {
			owner.setPets(ownerEntity.getPets()
				.stream()
				.map(PetEntityTranslator::toDomainModel)
				.collect(Collectors.toList()));
		}
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
