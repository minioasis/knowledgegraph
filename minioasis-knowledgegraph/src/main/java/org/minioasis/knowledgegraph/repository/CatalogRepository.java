package org.minioasis.knowledgegraph.repository;

import org.minioasis.knowledgegraph.domain.Catalog;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CatalogRepository extends Neo4jRepository<Catalog, Long>, CatalogRepositoryCustom {

}
