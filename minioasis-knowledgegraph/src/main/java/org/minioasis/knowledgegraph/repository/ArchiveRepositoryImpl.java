package org.minioasis.knowledgegraph.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minioasis.knowledgegraph.domain.Archive;
import org.minioasis.knowledgegraph.domain.criteria.ArchiveCriteria;
import org.neo4j.ogm.session.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ArchiveRepositoryImpl implements ArchiveRepositoryCustom {

	private final Session session;

	public ArchiveRepositoryImpl(Session session) {
		this.session = session;
	}
	
	public Page<Archive> findByCriteria(ArchiveCriteria criteria, Pageable pageable) {
		
		String name = criteria.getName();
		long offset = pageable.getOffset();
		final int limit = pageable.getPageSize();
		
		Map<String, Object> params = new HashMap<>();
		
        params.put ("name", "(?i).*" + name + ".*");
        params.put ("offset", offset);
        params.put ("limit", limit);
        
        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (a:Archive) WHERE a.name =~ $name RETURN a ORDER BY LOWER(a.name) ASC SKIP $offset LIMIT $limit";
        List <Archive> list = (List<Archive>) session.query(Archive.class, cypher, params);
        
        String totalQuery = "MATCH (a:Archive) WHERE a.name =~ $name RETURN count(*) AS total" ;
        Iterable<Map<String, Object>>  results =  session.query(totalQuery, params);
        
        long total = 0;
        
        for(Map<String, Object> row: results){
        	total = (long) row.get("total");
        }
           
        return new PageImpl<Archive>(list, pageable, total);
        
	}
}
