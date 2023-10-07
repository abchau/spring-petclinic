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
 class OwnerController {

	private final OwnerBoundary ownerBoundary;

	@Autowired
	public OwnerController(OwnerBoundary ownerBoundary) {
		this.ownerBoundary = ownerBoundary;
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

		Owner createdOwner = ownerBoundary.createOwner(owner);

		return "redirect:/owners/" + createdOwner.getId();
	}

	@GetMapping("/owners/{id}/edit")
	public String initUpdateOwnerForm(@PathVariable("id") Integer id, ModelMap model) {
		if (model.get("form") == null) {
			Owner owner = ownerBoundary.findById(id);
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

		ownerBoundary.updateOwner(owner);
		redirectAttributes.addFlashAttribute("success", true);

		return "redirect:/owners/{id}";
	}

	private static record CreateOwnerForm(
		@NotEmpty String firstName,
		@NotEmpty String lastName,
		@NotEmpty String address,
		@NotEmpty String city,
		@NotEmpty @Digits(fraction = 0, integer = 10) String telephone
	) {
		public static CreateOwnerForm empty() {
			return new CreateOwnerForm(null, null, null, null, null);
		}
	}

	private static record UpdateOwnerForm(
		@NotEmpty Integer id,
		@NotEmpty String firstName,
		@NotEmpty String lastName,
		@NotEmpty String address,
		@NotEmpty String city,
		@NotEmpty @Digits(fraction = 0, integer = 10) String telephone
	) {
		public static UpdateOwnerForm of(Owner owner) {
			return new UpdateOwnerForm(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getAddress(), owner.getCity(), owner.getTelephone());
		}
	}

}
