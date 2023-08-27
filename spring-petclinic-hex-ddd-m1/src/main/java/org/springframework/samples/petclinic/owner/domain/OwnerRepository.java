package org.springframework.samples.petclinic.owner.domain;

import java.util.List;

/**
 * Domain SPI
 * 
 * Please note that this is a DDD Repository not Spring Data Repository.
 * 
 * @author github.com/abchau
 */
public interface OwnerRepository {

	public Owner create(Owner owner);

	public Owner update(Owner owner);

	public PaginatedOwner findByLastName(String lastname, int page);

	public Owner findById(Integer ownerId);
	
	public Owner findWithPetsById(Integer ownerId);
	
	/**
	 * just a sloppy demo to show we can choose to get rid of dependency on
	 * <code>org.springframework.data.domain</code> outside <code>infrastructure</code>
	 * package.
	 */
	public static class PaginatedOwner {

		private int totalPages;

		private long totalElements;

		private List<Owner> content;

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

		public List<Owner> getContent() {
			return content;
		}

		public void setContent(List<Owner> content) {
			this.content = content;
		}

	}

}
