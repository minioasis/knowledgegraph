package org.minioasis.knowledgegraph.controller;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.service.KnowledgeGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/doc")
public class DocViewRemove {

	@Autowired
	private KnowledgeGraphService service;
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("doc", this.service.findById(id));
		
		return "doc";

	}
	
	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.GET)
	public String delete(@PathVariable("id") long id, Model model) {

		Doc doc = this.service.findById(id);
		
		if(doc != null) {
			this.service.deleteById(id);
		}else {
			return "error";
		}
	
		model.addAttribute("id", id);
		
		return "deleted";
		
	}
}
