package org.springframework.samples.petclinic.pet.domain.visit;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
public interface SaveVisitUseCase {

	public Visit create(Visit visit);

}
