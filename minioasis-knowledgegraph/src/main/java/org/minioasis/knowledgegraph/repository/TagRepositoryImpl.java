package org.minioasis.knowledgegraph.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minioasis.knowledgegraph.domain.Tag;
import org.neo4j.ogm.session.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class TagRepositoryImpl implements TagRepositoryCustom {

	private final Session session;

	public TagRepositoryImpl(Session session) {
		this.session = session;
	}
	
	public Page<Tag> findAllTags(Pageable pageable){
		
		long offset = pageable.getOffset();
		final int limit = pageable.getPageSize();
		
		Map<String, Object> params = new HashMap<>();

        params.put ("offset", offset);
        params.put ("limit", limit);
        
        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (t:Tag) RETURN t ORDER BY LOWER(t.name) ASC SKIP $offset LIMIT $limit";
        List <Tag> list = (List<Tag>) session.query(Tag.class, cypher, params);
        
        String totalQuery = "MATCH (t:Tag) RETURN count(*) AS total" ;
        Iterable<Map<String, Object>>  results =  session.query(totalQuery, params);
        
        long total = 0;
        
        for(Map<String, Object> row: results){
        	total = (long) row.get("total");
        }
           
        return new PageImpl<Tag>(list, pageable, total);
        
	}
	public Page<Tag> findByNameRegex(String name, Pageable pageable) {
		
		long offset = pageable.getOffset();
		final int limit = pageable.getPageSize();
		
		Map<String, Object> params = new HashMap<>();
		
        params.put ("name", "(?i).*" + name + ".*");
        params.put ("offset", offset);
        params.put ("limit", limit);
        
        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (t:Tag) WHERE t.name =~ $name RETURN t ORDER BY LOWER(t.name) ASC SKIP $offset LIMIT $limit";
        List <Tag> list = (List<Tag>) session.query(Tag.class, cypher, params);
        
        String totalQuery = "MATCH (t:Tag) WHERE t.name =~ $name RETURN count(*) AS total" ;
        Iterable<Map<String, Object>>  results =  session.query(totalQuery, params);
        
        long total = 0;
        
        for(Map<String, Object> row: results){
        	total = (long) row.get("total");
        }
           
        return new PageImpl<Tag>(list, pageable, total);
        
	}
}
