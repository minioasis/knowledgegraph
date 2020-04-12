package org.minioasis.knowledgegraph.controller;

import javax.servlet.http.HttpServletRequest;

import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.domain.criteria.CatalogCriteria;
import org.minioasis.knowledgegraph.service.KnowledgeGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@Controller
@RequestMapping("/admin/catalog")
public class CatalogListSearch {

	@Autowired
	private KnowledgeGraphService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String groups(Model model, HttpServletRequest request, Pageable pageable) {

		Page<Catalog> page = this.service.findAllCatalogs(pageable);
		
		String next = buildUri(request, page.getNumber() + 1);
		String previous = buildUri(request, page.getNumber() - 1);
		
		model.addAttribute("criteria", new CatalogCriteria());
		model.addAttribute("next", next);
		model.addAttribute("previous", previous);
		model.addAttribute("page", page);
		
		return "catalogs";
	}
	
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("criteria") CatalogCriteria criteria, HttpServletRequest request, 
			Model model, Pageable pageable) {

		Page<Catalog> page = this.service.findByCriteria(criteria, pageable);
		
		String next = buildUri(request, page.getNumber() + 1);
		String previous = buildUri(request, page.getNumber() - 1);

		model.addAttribute("next", next);
		model.addAttribute("previous", previous);
		model.addAttribute("page", page);
		
		return "catalogs";

	}
	
	private String buildUri(HttpServletRequest request, int page){
		UriComponents uc = ServletUriComponentsBuilder.fromRequest(request)
		        .replaceQueryParam("page", "{id}").build()
		        .expand(page);
		
		return uc.toUriString();
	}
}
