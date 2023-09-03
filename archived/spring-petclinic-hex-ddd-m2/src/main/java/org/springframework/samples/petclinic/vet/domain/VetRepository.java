package org.springframework.samples.petclinic.vet.domain;

import java.util.List;

// SPI
public interface VetRepository {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

	/**
	 * just a sloppy demo to show we can choose to get rid of dependency on
	 * <code>org.springframework.data.domain</code> outside <code>infrastructure</code>
	 * package
	 */
	public static class PaginatedVet {

		private int totalPages;

		private long totalElements;

		private List<Vet> content;

		public boolean isEmpty() {
			return content == null || content.size() == 0;
		}

		public int getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public List<Vet> getContent() {
			return content;
		}

		public void setContent(List<Vet> content) {
			this.content = content;
		}

	}

}
