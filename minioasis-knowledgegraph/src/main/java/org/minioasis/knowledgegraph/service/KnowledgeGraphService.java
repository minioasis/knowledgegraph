package org.minioasis.knowledgegraph.service;

import java.util.Optional;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.Tag;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.minioasis.knowledgegraph.domain.criteria.CatalogCriteria;
import org.minioasis.knowledgegraph.domain.criteria.DocCriteria;
import org.minioasis.knowledgegraph.repository.ArchiveRepository;
import org.minioasis.knowledgegraph.repository.CatalogRepository;
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
	private ArchiveRepository archiveRepository;
	@Autowired
	private CatalogRepository catalogRepository;
	@Autowired
	private DocRepository docRepository;
	@Autowired
	private TagRepository tagRepository;

	// Archive
	
	public void save(Archive entity) {
		archiveRepository.save(entity);
	}

	public void edit(Archive entity) {
		archiveRepository.save(entity);
	}

	public void delete(Archive entity) {
		archiveRepository.delete(entity);
	}

	public void deleteArchiveById(Long id) {
		archiveRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Archive findArchiveById(Long id) {

		Optional<Archive> entity = archiveRepository.findById(id);
		if (entity.isPresent()) {
			return entity.get();
		}
		return null;
	}

	public Page<Archive> findAllArchives(Pageable pageable) {
		return archiveRepository.findAll(pageable);
	}

	
	public Page<Archive> findByCriteria(ArchiveCriteria criteria, Pageable pageable) {
		return this.archiveRepository.findByCriteria(criteria, pageable);
	}
	
	// Catalog
	public void save(Catalog entity) {
		catalogRepository.save(entity);
	}

	public void edit(Catalog entity) {
		catalogRepository.save(entity);
	}

	public void delete(Catalog entity) {
		catalogRepository.delete(entity);
	}

	public void deleteCatalogById(Long id) {
		catalogRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Catalog findCatalogById(Long id) {

		Optional<Catalog> entity = catalogRepository.findById(id);
		if (entity.isPresent()) {
			return entity.get();
		}
		return null;
	}

	public Page<Catalog> findAllCatalogs(Pageable pageable) {
		return catalogRepository.findAll(pageable);
	}
	
	public Page<Catalog> findByCriteria(CatalogCriteria criteria, Pageable pageable){
		return this.catalogRepository.findByCriteria(criteria, pageable);
	}
	
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
