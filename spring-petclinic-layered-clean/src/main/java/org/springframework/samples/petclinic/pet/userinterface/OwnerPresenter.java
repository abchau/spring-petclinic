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
package org.springframework.samples.petclinic.pet.userinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.pet.application.OwnerBoundary;
import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author github.com/abchau
 */
@Controller
 class OwnerPresenter {

	private final OwnerBoundary ownerBoundary;

	@Autowired
	public OwnerPresenter(OwnerBoundary ownerBoundary) {
		this.ownerBoundary = ownerBoundary;
	}

	@GetMapping("/owners/find")
	public String initFindForm(@ModelAttribute("form") FindOwnerForm form, BindingResult result) {
		if (form.lastName() == null) {
			result.rejectValue("lastName", "notFound", "not found");
		}

		return "owners/findOwners";
	}

	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page,
			@ModelAttribute("form") FindOwnerForm form, BindingResult result, Model model) {
		// allow parameterless GET request for /owners to return all records
		if (form.lastName() == null) {
			form = FindOwnerForm.empty(); // empty string signifies broadest possible
											// search
		}

		// find owners by last name
		PaginatedOwner ownersResults = ownerBoundary.findPaginatedForOwnersLastName(form.lastName(), page);
		if (ownersResults.isEmpty()) {
			// no owners found
			return "redirect:/owners/find?lastName=" + form.lastName();
		}

		if (ownersResults.getTotalElements() == 1) {
			// 1 owner found
			Owner owner = ownersResults.getContent().get(0);
			return "redirect:/owners/" + owner.getId();
		}

		// multiple owners found
		model.addAttribute("form", form);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ownersResults.getTotalPages());
		model.addAttribute("totalItems", ownersResults.getTotalElements());
		model.addAttribute("listOwners", ownersResults.getContent());

		return "owners/ownersList";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{id}")
	public ModelAndView showOwner(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = ownerBoundary.findWithPetsById(id);
		mav.addObject(owner);
		return mav;
	}

	private static record FindOwnerForm(String lastName) {
		public static FindOwnerForm empty() {
			return new FindOwnerForm("");
		}
	}

}
