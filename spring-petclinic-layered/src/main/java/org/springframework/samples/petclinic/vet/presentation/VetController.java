/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet.presentation;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.springframework.samples.petclinic.vet.model.Vet;
import org.springframework.samples.petclinic.vet.service.VetService;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author github.com/abchau
 */
@Controller
public class VetController {

	private final VetService vetService;

	public VetController(VetService vetService) {
		this.vetService = vetService;
	}

	@GetMapping("/vets.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		Page<Vet> vets = vetService.findAll(page);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", vets.getTotalPages());
		model.addAttribute("totalItems", vets.getTotalElements());
		model.addAttribute("listVets", vets.getContent());

		return "vets/vetList";
	}

	@GetMapping({ "/vets.xml" })
	public @ResponseBody VetXmlView showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects so it is simpler for JSon/Object mapping
		VetXmlView vets = new VetXmlView();
		vets.setVets(vetService.findAll());
		return vets;
	}

	/**
	 * Simple View Model representing a list of veterinarians. Mostly here to be used for
	 * the 'vets' {@link org.springframework.web.servlet.view.xml.MarshallingView}.
	 *
	 * @author Arjen Poutsma
	 */
	@XmlRootElement
	private static class VetXmlView {

		private Collection<Vet> vets;

		@XmlElement
		public Collection<Vet> getVets() {
			return vets;
		}

		public void setVets(Collection<Vet> vets) {
			this.vets = vets;
		}

		@Override
		public String toString() {
			return "VetXmlView [vets=" + vets + "]";
		}

	}

}
