package org.minioasis.knowledgegraph.controller;

import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.service.KnowledgeGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/catalog")
public class CatalogViewRemove {

	@Autowired
	private KnowledgeGraphService service;
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("catalog", this.service.findCatalogById(id));
		
		return "catalog";

	}
	
	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.GET)
	public String delete(@PathVariable("id") long id, Model model) {

		Catalog catalog = this.service.findCatalogById(id);
		
		if(catalog != null) {
			this.service.deleteCatalogById(id);
		}else {
			return "error";
		}
	
		model.addAttribute("id", id);
		
		return "deleted";
		
	}
}
