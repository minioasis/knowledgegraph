package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Archive;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ArchiveRepository  extends Neo4jRepository<Archive, Long>, ArchiveRepositoryCustom {

}
