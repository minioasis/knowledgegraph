package org.minioasis.knowledgegraph.controller;

import javax.servlet.http.HttpServletRequest;

import org.minioasis.knowledgegraph.domain.Tag;
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
@RequestMapping("/admin/tag")
public class TagListSearch {

	@Autowired
	private KnowledgeGraphService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String groups(Model model, HttpServletRequest request, Pageable pageable) {

		Page<Tag> page = this.service.findAllTags(pageable);
		
		String next = buildUri(request, page.getNumber() + 1);
		String previous = buildUri(request, page.getNumber() - 1);
		
		model.addAttribute("name", "");
		model.addAttribute("next", next);
		model.addAttribute("previous", previous);
		model.addAttribute("page", page);
		
		return "tags";
	}
	
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(@ModelAttribute("name") String name, HttpServletRequest request, 
			Model model, Pageable pageable) {

		Page<Tag> page = this.service.findTagsByNameRegex(name, pageable);
		
		String next = buildUri(request, page.getNumber() + 1);
		String previous = buildUri(request, page.getNumber() - 1);

		model.addAttribute("next", next);
		model.addAttribute("previous", previous);
		model.addAttribute("page", page);
		
		return "tags";

	}
	
	private String buildUri(HttpServletRequest request, int page){
		UriComponents uc = ServletUriComponentsBuilder.fromRequest(request)
		        .replaceQueryParam("page", "{id}").build()
		        .expand(page);
		
		return uc.toUriString();
	}
}
