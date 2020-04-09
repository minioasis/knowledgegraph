package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Doc;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends Neo4jRepository<Doc, Long>{

	Doc findByTitle(@Param("title") String title);
}
