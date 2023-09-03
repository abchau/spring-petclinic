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
import org.springframework.data.domain.Page;
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

import org.springframework.samples.petclinic.pet.application.OwnerService;
import org.springframework.samples.petclinic.pet.domain.Owner;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author github.com/abchau
 */
@Controller
class OwnerController {

	private final OwnerService ownerService;

	@Autowired
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@GetMapping("/owners/new")
	public String initCreationForm(ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", CreateOwnerForm.empty());
		}

		model.put("mode", "new");
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/owners/new")
	public String processCreationForm(@ModelAttribute("form") @Valid CreateOwnerForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/new";
		}

		Owner owner = new Owner();
		owner.setFirstName(form.firstName());
		owner.setLastName(form.lastName());
		owner.setAddress(form.address());
		owner.setCity(form.city());
		owner.setTelephone(form.telephone());

		Owner createdOwner = ownerService.createOwner(owner);

		return "redirect:/owners/" + createdOwner.getId();
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
		Page<Owner> ownersResults = ownerService.findPaginatedForOwnersLastName(form.lastName(), page);
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

	@GetMapping("/owners/{id}/edit")
	public String initUpdateOwnerForm(@PathVariable("id") Integer id, ModelMap model) {
		if (model.get("form") == null) {
			Owner owner = ownerService.findById(id);
			model.put("form", UpdateOwnerForm.of(owner));
		}
		model.put("mode", "edit");
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/owners/{id}/edit")
	public String processUpdateOwnerForm(@Valid UpdateOwnerForm form, BindingResult result,
			RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{id}/edit";
		}

		Owner owner = new Owner();
		owner.setId(id);
		owner.setFirstName(form.firstName());
		owner.setLastName(form.lastName());
		owner.setAddress(form.address());
		owner.setCity(form.city());
		owner.setTelephone(form.telephone());

		ownerService.updateOwner(owner);
		redirectAttributes.addFlashAttribute("success", true);

		return "redirect:/owners/{id}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{id}")
	public ModelAndView showOwner(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = ownerService.findWithPetsById(id);
		mav.addObject(owner);
		return mav;
	}

	private static record FindOwnerForm(String lastName) {
		public static FindOwnerForm empty() {
			return new FindOwnerForm("");
		}
	}

	private static record CreateOwnerForm(@NotEmpty String firstName,

			@NotEmpty String lastName,

			@NotEmpty String address,

			@NotEmpty String city,

			@NotEmpty @Digits(fraction = 0, integer = 10) String telephone) {
		public static CreateOwnerForm empty() {
			return new CreateOwnerForm(null, null, null, null, null);
		}
	}

	private static record UpdateOwnerForm(Integer id,

			@NotEmpty String firstName,

			@NotEmpty String lastName,

			@NotEmpty String address,

			@NotEmpty String city,

			@NotEmpty @Digits(fraction = 0, integer = 10) String telephone) {
		public static UpdateOwnerForm of(Owner owner) {
			return new UpdateOwnerForm(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getAddress(),
					owner.getCity(), owner.getTelephone());
		}
	}

}
