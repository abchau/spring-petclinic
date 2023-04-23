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
package org.springframework.samples.petclinic.owner.userinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

// no source code dependency on output infrastructure
import org.springframework.samples.petclinic.owner.application.OwnerFacade;
import org.springframework.samples.petclinic.owner.application.OwnerFacade.CreateOwnerCommand;
import org.springframework.samples.petclinic.owner.application.OwnerFacade.UpdateOwnerCommand;
import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.owner.domain.OwnerRepository.PaginatedOwner;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author github.com/abchau
 */
@Controller
class OwnerController {

	private final OwnerFacade ownerFacade;

	@Autowired
	public OwnerController(OwnerFacade ownerFacade) {
		this.ownerFacade = ownerFacade;
	}

	@GetMapping("/owners/new")
	public String initCreationForm(ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", new CreateOwnerForm());
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

		CreateOwnerCommand createOwnerCommand = new CreateOwnerCommand();
		createOwnerCommand.setFirstName(form.getFirstName());
		createOwnerCommand.setLastName(form.getLastName());
		createOwnerCommand.setAddress(form.getAddress());
		createOwnerCommand.setCity(form.getCity());
		createOwnerCommand.setTelephone(form.getTelephone());

		Owner createdOwner = ownerFacade.createOwner(createOwnerCommand);

		// NOTE: should validate result here

		return "redirect:/owners/" + createdOwner.getId();
	}

	@GetMapping("/owners/find")
	public String initFindForm(@ModelAttribute("form") FindOwnerForm form, BindingResult result) {
		if (!form.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
		}

		return "owners/findOwners";
	}

	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page,
			@ModelAttribute("form") FindOwnerForm form, BindingResult result, Model model) {
		// allow parameterless GET request for /owners to return all records
		if (form.getLastName() == null) {
			form.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		PaginatedOwner ownersResults = ownerFacade.findPaginatedForOwnersLastName(page, form.getLastName());
		if (ownersResults.isEmpty()) {
			// no owners found
			return "redirect:/owners/find?lastName=" + form.lastName;
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
			Owner owner = ownerFacade.findById(id);
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

		// demostrates doing extra validation by comparing parh variable and form
		// value. Do it based on your preference. Not necessary to follow
		if (form.getId() != id) {
			redirectAttributes.addFlashAttribute("form", form);
			FieldError fieldError = new FieldError(Owner.class.getName(), "id", "gocha!");
			redirectAttributes.addFlashAttribute("errors", List.of(fieldError));
			return "redirect:/owners/{id}/edit";
		}

		UpdateOwnerCommand updateOwnerCommand = new UpdateOwnerCommand();
		updateOwnerCommand.setId(id);
		updateOwnerCommand.setFirstName(form.getFirstName());
		updateOwnerCommand.setLastName(form.getLastName());
		updateOwnerCommand.setAddress(form.getAddress());
		updateOwnerCommand.setCity(form.getCity());
		updateOwnerCommand.setTelephone(form.getTelephone());

		Owner updatedOwner = ownerFacade.updateOwner(updateOwnerCommand);

		// NOTE: should validate result here

		return "redirect:/owners/{ownerId}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{id}")
	public ModelAndView showOwner(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = ownerFacade.findWithPetsById(id);
		mav.addObject(owner);
		return mav;
	}

	// no one else on earth needs to know the existence of this class, hence private
	// static
	private static class FindOwnerForm {

		private String lastName;

		public boolean isEmpty() {
			return lastName == null || lastName.trim().length() == 0;
		}

		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		@Override
		public String toString() {
			return "FindOwnerForm [lastName=" + lastName + "]";
		}

	}

	// no one else on earth needs to know the existence of this class, hence private
	// static
	private static class CreateOwnerForm {

		@NotEmpty
		private String firstName;

		@NotEmpty
		private String lastName;

		@NotEmpty
		private String address;

		@NotEmpty
		private String city;

		@NotEmpty
		@Digits(fraction = 0, integer = 10)
		private String telephone;

		public String getFirstName() {
			return this.firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getAddress() {
			return this.address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return this.city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getTelephone() {
			return this.telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		@Override
		public String toString() {
			return "CreateOwnerForm [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
					+ ", city=" + city + ", telephone=" + telephone + "]";
		}

	}

	// no one else on earth needs to know the existence of this class, hence private
	// static
	private static class UpdateOwnerForm extends CreateOwnerForm {

		private Integer id;

		public static UpdateOwnerForm of(Owner owner) {
			UpdateOwnerForm form = new UpdateOwnerForm();
			form.id = owner.getId();
			form.setFirstName(owner.getFirstName());
			form.setLastName(owner.getLastName());
			form.setAddress(owner.getAddress());
			form.setCity(owner.getCity());
			form.setTelephone(owner.getTelephone());
			return form;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "UpdateOwnerForm [super=" + super.toString() + ", id=" + id + "]";
		}

	}

}
