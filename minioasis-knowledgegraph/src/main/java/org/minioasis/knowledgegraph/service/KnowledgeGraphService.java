package org.minioasis.knowledgegraph.service;

import java.util.Optional;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.criteria.DocCriteria;
import org.minioasis.knowledgegraph.repository.DocRepository;
import org.minioasis.knowledgegraph.repository.DocRepositoryCustom;
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
	private DocRepository docRepository;
	@Autowired
	private DocRepositoryCustom docRepositorycustom;
	
	public void save(Doc entity) {
		docRepository.save(entity);
	}
	
	public void edit(Doc entity) {
		docRepository.save(entity);
	}
	
	public void delete(Doc entity) {
		docRepository.delete(entity);
	}

	public void deleteById(Long id) {
		docRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
    public Doc findById(Long id) {
		
        Optional<Doc> entity = docRepository.findById(id);
        if(entity.isPresent()) {
        	return entity.get();
        }
        return null;
    }
	
	@Transactional(readOnly = true)
    public Doc findByTitle(String title) {
        Doc result = docRepository.findByTitle(title);
        return result;
    }
	
	public Page<Doc> findAllDocs(Pageable pageable){
		return docRepository.findAll(pageable);
	}
	
	public Page<Doc> findByCriteria(DocCriteria criteria, Pageable pageable){
		return docRepositorycustom.findByCriteria(criteria, pageable);
	}
}
