package org.minioasis.knowledgegraph.controller;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.minioasis.knowledgegraph.service.KnowledgeGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/archive")
public class ArchiveController {
	
	@Autowired
	private KnowledgeGraphService service;

	@RequestMapping("/save")
	public String create(Model model) {
		model.addAttribute("archive", new Archive());
		return "archive.form";
	}	
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String save(@ModelAttribute("archive") @Valid Archive archive, BindingResult result, Model model) {
		
		if(result.hasErrors()){	
			return "archive.form";			
		} else {		
			
			try{
				this.service.save(archive);
			} 
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("title","error.not.unique");			
				return "archive.form";				
			}
			
			model.addAttribute("archive", archive);
			
			return "redirect:/admin/archive/" + archive.getId();
			
		}			
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Long id, Model model) {

		Archive archive = this.service.findById(id);
		
		if(archive == null) {
			model.addAttribute("error", "ITEM NOT FOUND !");
			return "error";
		}
		
		model.addAttribute("archive", archive);
		return "archive.form";
		
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String edit(@ModelAttribute("archive") @Valid Archive archive, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "archive.form";
		}
		else 
		{
			try{
				this.service.edit(archive);
			}
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("title","error.not.unique");		
				return "archive.form";
			}
			
			model.addAttribute("archive", archive);
			
			return "archive";
			
		}
	}
	
	@RequestMapping(value = { "/{id}/add.parent" }, method = RequestMethod.GET)
	public String addParent(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("archive", this.service.findById(id));
		
		return "archive.form.add.parent";
		
	}
	
	@RequestMapping(value = { "/{id}/search.parent" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") ArchiveCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Archive> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("archive", this.service.findById(id));
		
		return "archive.form.add.parent";

	}
	
	@RequestMapping(value = { "/add.parent" }, method = RequestMethod.POST)
	public String addParent(@RequestParam(value = "archiveId", required = true) long archiveId,
			@RequestParam(value = "parentId", required = true) long parentId, Model model) {

		Archive parentArchive = this.service.findById(parentId);
		Archive archive = this.service.findById(archiveId);

		archive.addArchive(parentArchive);

		this.service.save(archive);

		model.addAttribute("archive", archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
}
