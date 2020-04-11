package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends Neo4jRepository<Tag, Long>,  TagRepositoryCustom {

	Tag findByName(@Param("name") String name);
	
	//Page<Tag> findByNameRegex(String tagName, Pageable pageable);
	
}
