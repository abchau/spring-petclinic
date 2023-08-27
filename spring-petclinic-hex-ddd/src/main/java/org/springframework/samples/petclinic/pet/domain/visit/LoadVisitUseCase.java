
package org.springframework.samples.petclinic.pet.domain.visit;

import java.util.List;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface LoadVisitUseCase {

	public List<Visit> findAllByPetId(Integer petId);

}
