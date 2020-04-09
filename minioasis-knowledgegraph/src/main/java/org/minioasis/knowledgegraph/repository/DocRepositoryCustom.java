package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.criteria.DocCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocRepositoryCustom {
	
	Page<Doc> findByCriteria(DocCriteria criteria, Pageable pageable);
}
