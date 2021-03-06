package org.minioasis.knowledgegraph.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.Tag;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.minioasis.knowledgegraph.domain.criteria.CatalogCriteria;
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
@RequestMapping("/admin/doc")
public class DocController {
	
	@Autowired
	private KnowledgeGraphService service;

	@RequestMapping("/save")
	public String create(Model model) {
		model.addAttribute("doc", new Doc());
		return "doc.form";
	}	

	// crud
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String save(@ModelAttribute("doc") @Valid Doc doc, BindingResult result, Model model) {
		
		if(result.hasErrors()){	
			return "doc.form";			
		} else {		
			
			try{
				this.service.save(doc);
			} 
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("title","error.not.unique");			
				return "doc.form";				
			}
			
			return "redirect:/admin/doc/" + doc.getId();
			
		}			
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Long id, Model model) {

		Doc doc = this.service.findDocById(id);
		
		if(doc == null) {
			model.addAttribute("error", "ITEM NOT FOUND !");
			return "error";
		}
		
		model.addAttribute("doc", doc);
		return "doc.form";
		
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String edit(@ModelAttribute("doc") @Valid Doc doc, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "doc.form";
		}
		else 
		{
			try{
				
				doc.setUpdated(LocalDateTime.now());
				this.service.edit(doc);
			}
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("title","error.not.unique");		
				return "doc.form";
			}
			
			return "redirect:/admin/doc/" + doc.getId();
			
		}
	}
	
	// add doc
	@RequestMapping(value = { "/{id}/add.related" }, method = RequestMethod.GET)
	public String addRelated(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.related";
		
	}
	
	@RequestMapping(value = { "/{id}/search.related" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") DocCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Doc> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.related";

	}
	
	@RequestMapping(value = { "/add.related" }, method = RequestMethod.POST)
	public String addRelated(@RequestParam(value = "docId", required = true) long docId,
			@RequestParam(value = "relatedId", required = true) long relatedId, Model model) {

		Doc relatedDoc = this.service.findDocById(relatedId);
		Doc doc = this.service.findDocById(docId);

		doc.addDoc(relatedDoc);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.related/{relatedId}" }, method = RequestMethod.GET)
	public String removeRelated(@PathVariable("id") long id, @PathVariable("relatedId") long relatedId, Model model) {

		Doc relatedDoc = this.service.findDocById(relatedId);
		Doc doc = this.service.findDocById(id);

		doc.removeDoc(relatedDoc);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	// add tag
	@RequestMapping(value = { "/{id}/add.tag" }, method = RequestMethod.GET)
	public String addTag(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.tag";
		
	}
	
	@RequestMapping(value = { "/{id}/search.tag" }, method = RequestMethod.GET)
	public String searchTag(@ModelAttribute("name") String name, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		final String nameRegex = "(?i).*" + name + ".*";
		Page<Tag> page = this.service.findTagsByNameRegex(nameRegex, pageable);

		model.addAttribute("page", page);
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.tag";

	}
	
	@RequestMapping(value = { "/add.tag" }, method = RequestMethod.POST)
	public String addTag(@RequestParam(value = "docId", required = true) long docId,
			@RequestParam(value = "tagId", required = true) long tagId, Model model) {

		Tag tag = this.service.findTagById(tagId);
		Doc doc = this.service.findDocById(docId);

		doc.addTag(tag);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.tag/{tagId}" }, method = RequestMethod.GET)
	public String removeTag(@PathVariable("id") long id, @PathVariable("tagId") long tagId, Model model) {

		Tag tag = this.service.findTagById(tagId);
		Doc doc = this.service.findDocById(id);

		doc.removeTag(tag);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	// add catalog
	@RequestMapping(value = { "/{id}/add.catalog" }, method = RequestMethod.GET)
	public String addCatalog(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.catalog";
		
	}
	
	@RequestMapping(value = { "/{id}/search.catalog" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") CatalogCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Catalog> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.catalog";

	}
	
	@RequestMapping(value = { "/add.catalog" }, method = RequestMethod.POST)
	public String addCatalog(@RequestParam(value = "docId", required = true) long docId,
			@RequestParam(value = "catalogId", required = true) long catalogId, Model model) {

		Catalog catalog = this.service.findCatalogById(catalogId);
		Doc doc = this.service.findDocById(docId);

		doc.addCatalog(catalog);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.catalog/{catalogId}" }, method = RequestMethod.GET)
	public String removeCatalog(@PathVariable("id") long id, @PathVariable("catalogId") long catalogId, Model model) {

		Catalog catalog = this.service.findCatalogById(catalogId);
		Doc doc = this.service.findDocById(id);

		doc.removeCatalog(catalog);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	// add archive
	@RequestMapping(value = { "/{id}/add.archive" }, method = RequestMethod.GET)
	public String addArchive(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.archive";
		
	}
	
	@RequestMapping(value = { "/{id}/search.archive" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") ArchiveCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Archive> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.archive";

	}
	
	@RequestMapping(value = { "/add.archive" }, method = RequestMethod.POST)
	public String addArchive(@RequestParam(value = "docId", required = true) long docId,
			@RequestParam(value = "archiveId", required = true) long archiveId, Model model) {

		Archive archive = this.service.findArchiveById(archiveId);
		Doc doc = this.service.findDocById(docId);

		doc.addArchive(archive);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.archive/{archiveId}" }, method = RequestMethod.GET)
	public String removeArchive(@PathVariable("id") long id, @PathVariable("archiveId") long archiveId, Model model) {

		Archive archive = this.service.findArchiveById(archiveId);
		Doc doc = this.service.findDocById(id);

		doc.removeArchive(archive);

		this.service.save(doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
}
