package org.minioasis.knowledgegraph.controller;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.domain.Doc;
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
@RequestMapping("/admin/catalog")
public class CatalogController {

	@Autowired
	private KnowledgeGraphService service;
	
	@RequestMapping("/save")
	public String create(Model model) {
		model.addAttribute("catalog", new Catalog());
		return "catalog.form";
	}	
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String save(@ModelAttribute("catalog") @Valid Catalog catalog, BindingResult result, Model model) {
		
		if(result.hasErrors()){	
			return "catalog.form";			
		} else {		
			
			try{
				this.service.save(catalog);
			} 
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("name","error.not.unique");			
				return "catalog.form";				
			}
			
			return "redirect:/admin/catalog/" + catalog.getId();
			
		}			
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Long id, Model model) {

		Catalog catalog = this.service.findCatalogById(id);
		
		if(catalog == null) {
			model.addAttribute("error", "ITEM NOT FOUND !");
			return "error";
		}
		
		model.addAttribute("catalog", catalog);
		return "catalog.form";
		
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String edit(@ModelAttribute("catalog") @Valid Catalog catalog, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "catalog.form";
		}
		else 
		{
			try{

				this.service.edit(catalog);
			}
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("name","error.not.unique");		
				return "catalog.form";
			}
			
			return "redirect:/admin/catalog/" + catalog.getId();
			
		}
	}
	
	@RequestMapping(value = { "/{id}/add.doc" }, method = RequestMethod.GET)
	public String addRelated(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog.form.add.doc";
		
	}
	
	@RequestMapping(value = { "/{id}/search.doc" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") DocCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Doc> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog.form.add.doc";

	}
	
	@RequestMapping(value = { "/add.doc" }, method = RequestMethod.POST)
	public String addRelated(@RequestParam(value = "catalogId", required = true) long catalogId,
			@RequestParam(value = "docId", required = true) long docId, Model model) {

		Doc doc = this.service.findDocById(docId);
		Catalog catalog = this.service.findCatalogById(catalogId);

		catalog.addDoc(doc);

		this.service.save(catalog);

		return "redirect:/admin/catalog/" + catalog.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.doc/{docId}" }, method = RequestMethod.GET)
	public String removeRelated(@PathVariable("id") long id, @PathVariable("docId") long docId, Model model) {

		Doc doc = this.service.findDocById(docId);
		Catalog catalog = this.service.findCatalogById(id);

		catalog.removeDoc(doc);

		this.service.save(catalog);

		return "redirect:/admin/catalog/" + catalog.getId();

	}
	
	@RequestMapping(value = { "/{id}/add.parent" }, method = RequestMethod.GET)
	public String addParent(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog.form.add.parent";
		
	}
	
	@RequestMapping(value = { "/{id}/search.parent" }, method = RequestMethod.GET)
	public String searchParent(@ModelAttribute("criteria") CatalogCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Catalog> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog.form.add.parent";

	}
	
	@RequestMapping(value = { "/add.parent" }, method = RequestMethod.POST)
	public String addParent(@RequestParam(value = "catalogId", required = true) long catalogId,
			@RequestParam(value = "parentId", required = true) long parentId, Model model) {

		Catalog parent = this.service.findCatalogById(parentId);
		Catalog catalog = this.service.findCatalogById(catalogId);

		catalog.setParent(parent);

		this.service.save(catalog);

		return "redirect:/admin/catalog/" + catalog.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.parent" }, method = RequestMethod.GET)
	public String removeParent(@PathVariable("id") long id, Model model) {

		Catalog catalog = this.service.findCatalogById(id);
		catalog.setParent(null);
		this.service.save(catalog);

		return "redirect:/admin/catalog/" + catalog.getId();

	}
	
	@RequestMapping(value = { "/{id}/add.children" }, method = RequestMethod.GET)
	public String addChildren(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog.form.add.children";
		
	}
	
	@RequestMapping(value = { "/{id}/search.children" }, method = RequestMethod.GET)
	public String searchChildren(@ModelAttribute("criteria") CatalogCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Catalog> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog.form.add.children";

	}
	
	@RequestMapping(value = { "/add.children" }, method = RequestMethod.POST)
	public String addChildren(@RequestParam(value = "catalogId", required = true) long catalogId,
			@RequestParam(value = "childrenId", required = true) long childrenId, Model model) {

		Catalog children = this.service.findCatalogById(childrenId);
		Catalog catalog = this.service.findCatalogById(catalogId);

		catalog.addChildren(children);

		this.service.save(catalog);

		return "redirect:/admin/catalog/" + catalog.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.children/{childrenId}" }, method = RequestMethod.GET)
	public String removeChildren(@PathVariable("id") long id, @PathVariable("childrenId") long childrenId, Model model) {

		Catalog catalog = this.service.findCatalogById(id);
		Catalog children = this.service.findCatalogById(childrenId);

		catalog.removeChildren(children);

		this.service.save(catalog);

		return "redirect:/admin/catalog/" + catalog.getId();

	}
	
}
