package org.springframework.samples.petclinic.pet.application.drivenport;

import java.util.List;

import org.springframework.samples.petclinic.pet.domain.Visit;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface LoadVisitPort {

	public List<Visit> findAllByPetId(Integer petId);

}
