package org.minioasis.knowledgegraph.service;

import java.util.Optional;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.minioasis.knowledgegraph.repository.ArchiveRepository;
import org.minioasis.knowledgegraph.repository.ArchiveRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class KnowledgeGraphService {

	private final static Logger LOG = LoggerFactory.getLogger(KnowledgeGraphService.class);
	
	@Autowired
	private ArchiveRepository archiveRepository;
	@Autowired
	private ArchiveRepositoryCustom archiveRepositorycustom;
	
	public void save(Archive entity) {
		archiveRepository.save(entity);
	}
	
	public void edit(Archive entity) {
		archiveRepository.save(entity);
	}
	
	public void delete(Archive entity) {
		archiveRepository.delete(entity);
	}

	public void deleteById(Long id) {
		archiveRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
    public Archive findById(Long id) {
		
        Optional<Archive> entity = archiveRepository.findById(id);
        if(entity.isPresent()) {
        	return entity.get();
        }
        return null;
    }
	
	@Transactional(readOnly = true)
    public Archive findByTitle(String title) {
        Archive result = archiveRepository.findByTitle(title);
        return result;
    }
	
	public Page<Archive> findAllArchives(Pageable pageable){
		return archiveRepository.findAll(pageable);
	}
	
	public Page<Archive> findByCriteria(ArchiveCriteria criteria, Pageable pageable){
		return archiveRepositorycustom.findByCriteria(criteria, pageable);
	}
}
