package org.minioasis.knowledgegraph.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

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
			
			model.addAttribute("doc", doc);
			
			return "redirect:/admin/doc/" + doc.getId();
			
		}			
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Long id, Model model) {

		Doc doc = this.service.findById(id);
		
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
			
			model.addAttribute("doc", doc);
			
			return "doc";
			
		}
	}
	
	@RequestMapping(value = { "/{id}/add.parent" }, method = RequestMethod.GET)
	public String addParent(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("doc", this.service.findById(id));
		
		return "doc.form.add.parent";
		
	}
	
	@RequestMapping(value = { "/{id}/search.parent" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") DocCriteria criteria, @PathVariable("id") long id, 
			Model model, Pageable pageable) {

		Page<Doc> page = this.service.findByCriteria(criteria, pageable);

		model.addAttribute("page", page);
		model.addAttribute("doc", this.service.findById(id));
		
		return "doc.form.add.parent";

	}
	
	@RequestMapping(value = { "/add.parent" }, method = RequestMethod.POST)
	public String addParent(@RequestParam(value = "docId", required = true) long docId,
			@RequestParam(value = "parentId", required = true) long parentId, Model model) {

		Doc parentDoc = this.service.findById(parentId);
		Doc doc = this.service.findById(docId);

		doc.addDoc(parentDoc);

		this.service.save(doc);

		model.addAttribute("doc", doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
	
	@RequestMapping(value = { "/{id}/remove.parent/{parentId}" }, method = RequestMethod.GET)
	public String removeParent(@PathVariable("id") long id, @PathVariable("parentId") long parentId, Model model) {

		Doc parentDoc = this.service.findById(parentId);
		Doc doc = this.service.findById(id);

		doc.removeDoc(parentDoc);

		this.service.save(doc);

		model.addAttribute("doc", doc);

		return "redirect:/admin/doc/" + doc.getId();

	}
}
