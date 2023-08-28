package org.springframework.samples.petclinic.pet.application.drivenport;

import org.springframework.samples.petclinic.pet.domain.Visit;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface SaveVisitPort{

	public Visit create(Visit visit);

}
