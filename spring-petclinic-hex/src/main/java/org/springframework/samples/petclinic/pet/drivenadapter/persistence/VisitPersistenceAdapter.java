package org.springframework.samples.petclinic.pet.drivenadapter.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.application.drivenport.LoadVisitPort;
import org.springframework.samples.petclinic.pet.application.drivenport.SaveVisitPort;
import org.springframework.samples.petclinic.pet.domain.Visit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@Service
public class VisitPersistenceAdapter implements LoadVisitPort, SaveVisitPort {

	private final VisitEntityRepository visitEntityRepository;

	public VisitPersistenceAdapter(VisitEntityRepository visitEntityRepository) {
		this.visitEntityRepository = visitEntityRepository;
	}

	@Override
	@Transactional
	public Visit create(Visit newVisit) {
		VisitEntity newVisitEntity = VisitEntityTranslator.toPersistenceModel(newVisit);
		VisitEntity savedVisitEntity = visitEntityRepository.save(newVisitEntity);
		Visit savedVisit = VisitEntityTranslator.toDomainModel(savedVisitEntity);
		return savedVisit;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Visit> findAllByPetId(Integer petId) {
		return visitEntityRepository.findAllByPetId(petId)
			.stream()
			.map(VisitEntityTranslator::toDomainModel)
			.collect(Collectors.toList());
	}

}
