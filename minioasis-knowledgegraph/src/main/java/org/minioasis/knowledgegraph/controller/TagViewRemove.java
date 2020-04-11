package org.minioasis.knowledgegraph.controller;

import org.minioasis.knowledgegraph.domain.Tag;
import org.minioasis.knowledgegraph.service.KnowledgeGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/tag")
public class TagViewRemove {

	@Autowired
	private KnowledgeGraphService service;
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("tag", this.service.findTagById(id));
		
		return "tag";

	}
	
	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.GET)
	public String delete(@PathVariable("id") long id, Model model) {

		Tag tag = this.service.findTagById(id);
		
		if(tag != null) {
			this.service.deleteTagById(id);
		}else {
			return "error";
		}
	
		model.addAttribute("id", id);
		
		return "deleted";
		
	}
}
