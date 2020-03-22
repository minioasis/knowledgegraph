package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Archive;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends Neo4jRepository<Archive, Long>{

	Archive findByTitle(@Param("title") String title);
}
