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
package org.springframework.samples.petclinic.pet.domain;

import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

/**
 * A DDD Repository (Don't confuse with Spring Data Repository)
 * A DDD Domain Service
 *
 * @author github.com/abchau
 */
public interface OwnerRepository {

	public Owner create(Owner owner);

	public Owner update(Owner owner);

	public PaginatedOwner findByLastName(String lastname, int page);

	public Owner findById(Integer ownerId);

	public Owner findWithPetsById(Integer ownerId);

}
