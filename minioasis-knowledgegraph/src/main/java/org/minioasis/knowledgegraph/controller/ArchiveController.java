package org.minioasis.knowledgegraph.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
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
				archive.setCreated(LocalDateTime.now());
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
	
	// add doc
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
														@RequestParam(value = "docIds", required = false) long [] docIds, Model model) {

		Archive archive = this.service.findArchiveById(archiveId);
		
		// add multiple docs
		if(archiveId > -1 && docId == -1 && docIds != null &&  docIds.length > 0) {
			
			for (long id : docIds) {
				Doc doc = this.service.findDocById(id);
				archive.addDoc(doc);
			}
			
		}
		
		// add one doc
		if(archiveId > -1 && docId > -1){
			Doc doc = this.service.findDocById(docId);
			archive.addDoc(doc);
		}

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
	
	// add children archive
	@RequestMapping(value = { "/{id}/add.children" }, method = RequestMethod.GET)
	public String addChildren(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("archive", this.service.findArchiveById(id));
		
		return "archive.form.add.children";
		
	}
	
	@RequestMapping(value = { "/{id}/search.children" }, method = RequestMethod.GET)
	public String searchChildren(@ModelAttribute("criteria") ArchiveCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Archive> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("archive", this.service.findArchiveById(id));
		
		return "archive.form.add.children";

	}
	
	@RequestMapping(value = { "/add.children" }, method = RequestMethod.POST)
	public String addChildren(@RequestParam(value = "archiveId", required = true) long archiveId,
														@RequestParam(value = "childrenId") long childrenId, 
														@RequestParam(value = "childrenIds", required = false) long [] childrenIds, Model model) {

		Archive archive = this.service.findArchiveById(archiveId);
		
		// add multiple childrens
		if(archiveId > -1 && childrenId == -1 && childrenIds != null &&  childrenIds.length > 0) {
			
			for (long id : childrenIds) {
				Archive children = this.service.findArchiveById(id);
				archive.addChildren(children);
			}
			
		}
		
		// add one children
		if(archiveId > -1 && childrenId > -1){
			Archive children = this.service.findArchiveById(childrenId);
			archive.addChildren(children);
		}

		this.service.save(archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.children/{childrenId}" }, method = RequestMethod.GET)
	public String removeChildren(@PathVariable("id") long id, @PathVariable("childrenId") long childrenId, Model model) {

		Archive archive = this.service.findArchiveById(id);
		Archive children = this.service.findArchiveById(childrenId);

		archive.removeChildren(children);

		this.service.save(archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
	
	// add parent archive
	@RequestMapping(value = { "/{id}/add.parent" }, method = RequestMethod.GET)
	public String addParent(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("archive", this.service.findArchiveById(id));
		
		return "archive.form.add.parent";
		
	}
	
	@RequestMapping(value = { "/{id}/search.parent" }, method = RequestMethod.GET)
	public String searchParent(@ModelAttribute("criteria") ArchiveCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Archive> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("archive", this.service.findArchiveById(id));
		
		return "archive.form.add.parent";

	}
	
	@RequestMapping(value = { "/add.parent" }, method = RequestMethod.POST)
	public String addParent(@RequestParam(value = "archiveId", required = true) long archiveId,
														 @RequestParam(value = "parentId") long parentId, Model model) {

		Archive archive = this.service.findArchiveById(archiveId);

		Archive parent = this.service.findArchiveById(parentId);
		archive.addParent(parent);

		this.service.save(archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.parent/{parentId}" }, method = RequestMethod.GET)
	public String removeParent(@PathVariable("id") long id, @PathVariable("parentId") long parentId, Model model) {

		Archive archive = this.service.findArchiveById(id);
		Archive parent = this.service.findArchiveById(parentId);

		archive.removeParent(parent);

		this.service.save(archive);

		return "redirect:/admin/archive/" + archive.getId();

	}
}
