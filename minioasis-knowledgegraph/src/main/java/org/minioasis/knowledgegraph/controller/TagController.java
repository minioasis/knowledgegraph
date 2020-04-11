package org.minioasis.knowledgegraph.controller;

import javax.validation.Valid;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.Tag;
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
@RequestMapping("/admin/tag")
public class TagController {

	@Autowired
	private KnowledgeGraphService service;

	@RequestMapping("/save")
	public String create(Model model) {
		model.addAttribute("tag", new Tag());
		return "tag.form";
	}	
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String save(@ModelAttribute("tag") @Valid Tag tag, BindingResult result, Model model) {
		
		if(result.hasErrors()){	
			return "tag.form";			
		} else {		
			
			try{
				this.service.save(tag);
			} 
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("name","error.not.unique");			
				return "tag.form";				
			}
			
			return "redirect:/admin/tag/" + tag.getId();
			
		}			
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Long id, Model model) {

		Tag tag = this.service.findTagById(id);
		
		if(tag == null) {
			model.addAttribute("error", "ITEM NOT FOUND !");
			return "error";
		}
		
		model.addAttribute("tag", tag);
		return "tag.form";
		
	}
	
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String edit(@ModelAttribute("tag") @Valid Tag tag, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "tag.form";
		}
		else 
		{
			try{
				
				this.service.edit(tag);
			}
			catch (DataIntegrityViolationException eive)
			{
				result.rejectValue("name","error.not.unique");		
				return "tag.form";
			}
			
			return "redirect:/admin/tag/" + tag.getId();
			
		}
	}
	
	@RequestMapping(value = { "/{id}/remove.doc/{docId}" }, method = RequestMethod.GET)
	public String removeRelated(@PathVariable("id") long id, @PathVariable("docId") long docId, Model model) {

		Doc doc = this.service.findDocById(docId);
		Tag tag = this.service.findTagById(id);

		tag.removeDoc(doc);

		this.service.save(tag);

		return "redirect:/admin/tag/" + tag.getId();

	}
	
}
