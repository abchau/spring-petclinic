package org.springframework.samples.petclinic.pet.application.drivenport;

import java.util.List;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.pet.application.Visit;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface LoadVisitPort {

	public List<Visit> findAllByPetId(Integer petId);

}
