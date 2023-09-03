package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.domain.visit.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.domain.visit.LoadVisitUseCase;
import org.springframework.samples.petclinic.pet.domain.visit.SaveVisitUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It can be a local transaction or remote RPC to other services, cloud services, etc
 */
@Service
public class VisitPersistenceAdapater implements LoadVisitUseCase, SaveVisitUseCase {

	private final VisitEntityRepository visitEntityRepository;

	@Autowired
	public VisitPersistenceAdapater(VisitEntityRepository visitEntityRepository) {
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
