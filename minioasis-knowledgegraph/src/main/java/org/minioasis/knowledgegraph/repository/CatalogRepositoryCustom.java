package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.minioasis.knowledgegraph.domain.criteria.CatalogCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogRepositoryCustom {
	
	public Page<Catalog> findByCriteria(CatalogCriteria criteria, Pageable pageable) ;
}
