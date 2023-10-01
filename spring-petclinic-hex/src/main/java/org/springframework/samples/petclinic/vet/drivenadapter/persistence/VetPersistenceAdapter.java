package org.springframework.samples.petclinic.vet.drivenadapter.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.application.Specialty;
import org.springframework.samples.petclinic.vet.application.Vet;
import org.springframework.samples.petclinic.vet.application.Vet.PaginatedVet;
import org.springframework.samples.petclinic.vet.application.drivenadapter.LoadVetPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Technology-specific provider of a Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryAdapter
@Service
class VetPersistenceAdapter implements LoadVetPort {

	private final VetEntityRepository vetEntityRepository;

	public VetPersistenceAdapter(VetEntityRepository vetEntityRepository) {
		this.vetEntityRepository = vetEntityRepository;
	}

	@Override
	public List<Vet> findAll() {
		return vetEntityRepository.findAll().stream()
			.map(VetPersistenceAdapter::translateToDomainModel)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedVet findPaginatedVet(int page) {
		Pageable pageable = PageRequest.of(page - 1, 5);

		Page<VetEntity> jpaEntities = vetEntityRepository.findAll(pageable);
		List<Vet> vets = jpaEntities.stream()
			.map(VetPersistenceAdapter::translateToDomainModel)
			.collect(Collectors.toList());

		Page<Vet> result = new PageImpl<Vet>(vets, pageable, jpaEntities.getTotalElements());

		PaginatedVet paginatedVet = new PaginatedVet();
		paginatedVet.setTotalPages(result.getTotalPages());
		paginatedVet.setTotalElements(result.getTotalElements());
		paginatedVet.setContent(vets);

		return paginatedVet;
	}

	static Vet translateToDomainModel(VetEntity vetEntity) {
		Vet vet = new Vet();
		vet.setId(vetEntity.getId());
		vet.setFirstName(vetEntity.getFirstName());
		vet.setLastName(vetEntity.getLastName());
		vet.setSpecialties(vetEntity.getSpecialties().stream()
			.map(VetPersistenceAdapter::translateToDomainModel)
			.collect(Collectors.toList()));

		return vet;
	}

	static VetEntity translateToPersistenceModel(Vet vet) {
		VetEntity vetEntity = new VetEntity();
		vetEntity.setId(vet.getId());
		vetEntity.setFirstName(vet.getFirstName());
		vetEntity.setLastName(vet.getLastName());
		vetEntity.setSpecialties(vet.getSpecialties().stream()
			.map(VetPersistenceAdapter::translateToPersistenceModel)
			.collect(Collectors.toList()));

		return vetEntity;
	}

	static Specialty translateToDomainModel(SpecialtyEntity specialtyEntity) {
		Specialty specialty = new Specialty();
		specialty.setId(specialtyEntity.getId());
		specialty.setName(specialtyEntity.getName());

		return specialty;
	}

	static SpecialtyEntity translateToPersistenceModel(Specialty specialty) {
		SpecialtyEntity specialtyEntity = new SpecialtyEntity();
		specialtyEntity.setId(specialty.getId());
		specialtyEntity.setName(specialty.getName());

		return specialtyEntity;
	}
}
