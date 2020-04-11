package org.minioasis.knowledgegraph.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.Tag;
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
	
	@RequestMapping(value = { "/{id}/add.tag" }, method = RequestMethod.GET)
	public String addTag(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("doc", this.service.findDocById(id));
		
		return "doc.form.add.tag";
		
	}
	
	@RequestMapping(value = { "/{id}/search.tag" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("name") String name, @PathVariable("id") long id, 
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
}
