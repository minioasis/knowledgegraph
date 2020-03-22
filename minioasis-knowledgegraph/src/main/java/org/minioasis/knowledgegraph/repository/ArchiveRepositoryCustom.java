package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveRepositoryCustom {
	
	Page<Archive> findByCriteria(ArchiveCriteria criteria, Pageable pageable);
}
