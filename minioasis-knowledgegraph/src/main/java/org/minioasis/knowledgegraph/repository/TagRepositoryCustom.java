package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepositoryCustom {

	Page<Tag> findAllTags(Pageable pageable);
	
	Page<Tag> findByNameRegex(String name, Pageable pageable) ;
}
