package org.minioasis.knowledgegraph.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.criteria.DocCriteria;
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
				result.rejectValue("name","error.not.unique");			
				return "archive.form";				
			}
			
			return "redirect:/admin/archive/" + archive.getId();
			
		}			
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Long id, Model model) {

		Archive archive = this.service.findArchiveById(id);
		
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
				
				archive.setUpdated(LocalDateTime.now());
				this.service.edit(archive);
			}
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("name","error.not.unique");		
				return "archive.form";
			}
			
			return "redirect:/admin/archive/" + archive.getId();
			
		}
	}
	
	@RequestMapping(value = { "/{id}/add.doc" }, method = RequestMethod.GET)
	public String addRelated(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("archive", this.service.findArchiveById(id));
		
		return "archive.form.add.doc";
		
	}
	
	@RequestMapping(value = { "/{id}/search.doc" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") DocCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Doc> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("archive", this.service.findArchiveById(id));
		
		return "archive.form.add.doc";

	}
	
	@RequestMapping(value = { "/add.doc" }, method = RequestMethod.POST)
	public String addRelated(@RequestParam(value = "archiveId", required = true) long archiveId,
														@RequestParam(value = "docId") long docId, 
														@RequestParam(value = "docIds") long [] docIds, Model model) {

		Archive archive = this.service.findArchiveById(archiveId);
		
		if(archiveId > -1 && docId == -1 && docIds.length > 0) {
			
			for (long id : docIds) {
				Doc doc = this.service.findDocById(id);
				archive.addDoc(doc);
			}
			
		}

		Doc doc = this.service.findDocById(docId);
		archive.addDoc(doc);

		this.service.save(archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.doc/{docId}" }, method = RequestMethod.GET)
	public String removeRelated(@PathVariable("id") long id, @PathVariable("docId") long docId, Model model) {

		Doc doc = this.service.findDocById(docId);
		Archive archive = this.service.findArchiveById(id);

		archive.removeDoc(doc);

		this.service.save(archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
}
