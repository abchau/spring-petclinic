package org.springframework.samples.petclinic.visit.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.visit.domain.Visit;
import org.springframework.samples.petclinic.visit.domain.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It can be a local transaction or remote RPC to other services, cloud services, etc
 */
@Service
public class VisitRepositoryProvider implements VisitRepository {

	private final VisitEntityRepository visitEntityRepository;

	public VisitRepositoryProvider(VisitEntityRepository visitEntityRepository) {
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
