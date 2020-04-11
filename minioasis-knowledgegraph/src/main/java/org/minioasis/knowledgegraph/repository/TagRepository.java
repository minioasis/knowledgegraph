package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends Neo4jRepository<Tag, Long>{

	Tag findByName(@Param("name") String name);
	
	Page<Tag> findByNameRegex(String name, Pageable pageable);
	
}
