package org.minioasis.knowledgegraph.service;

import java.util.Optional;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.Tag;
import org.minioasis.knowledgegraph.domain.criteria.DocCriteria;
import org.minioasis.knowledgegraph.repository.DocRepository;
import org.minioasis.knowledgegraph.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class KnowledgeGraphService {

	private final static Logger LOG = LoggerFactory.getLogger(KnowledgeGraphService.class);

	@Autowired
	private DocRepository docRepository;
	@Autowired
	private TagRepository tagRepository;

	// Doc
	public void save(Doc entity) {
		docRepository.save(entity);
	}

	public void edit(Doc entity) {
		docRepository.save(entity);
	}

	public void delete(Doc entity) {
		docRepository.delete(entity);
	}

	public void deleteDocById(Long id) {
		docRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Doc findDocById(Long id) {

		Optional<Doc> entity = docRepository.findById(id);
		if (entity.isPresent()) {
			return entity.get();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Doc findDocByTitle(String title) {
		Doc result = docRepository.findByTitle(title);
		return result;
	}

	public Page<Doc> findAllDocs(Pageable pageable) {
		return docRepository.findAll(pageable);
	}

	public Page<Doc> findByCriteria(DocCriteria criteria, Pageable pageable) {
		return docRepository.findByCriteria(criteria, pageable);
	}

	// Tag
	public void save(Tag entity) {
		tagRepository.save(entity);
	}

	public void edit(Tag entity) {
		tagRepository.save(entity);
	}

	public void delete(Tag entity) {
		tagRepository.delete(entity);
	}

	public void deleteTagById(Long id) {
		tagRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Tag findTagById(Long id) {

		Optional<Tag> entity = tagRepository.findById(id);
		if (entity.isPresent()) {
			return entity.get();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Tag findTagByName(String name) {
		Tag result = tagRepository.findByName(name);
		return result;
	}

	public Page<Tag> findAllTags(Pageable pageable) {
		return tagRepository.findAllTags(pageable);
	}

	public Page<Tag> findTagsByNameRegex(String name, Pageable pageable) {
		return tagRepository.findByNameRegex(name, pageable);
	}
}
