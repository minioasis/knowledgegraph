package org.minioasis.knowledgegraph.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minioasis.knowledgegraph.domain.Doc;
import org.minioasis.knowledgegraph.domain.criteria.DocCriteria;
import org.neo4j.ogm.session.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DocRepositoryImpl implements DocRepositoryCustom {

	private final Session session;

	public DocRepositoryImpl(Session session) {
		this.session = session;
	}
	
	public Page<Doc> findByCriteria(DocCriteria criteria, Pageable pageable) {
		
		String title = criteria.getTitle();
		long offset = pageable.getOffset();
		final int limit = pageable.getPageSize();
		
		Map<String, Object> params = new HashMap<>();
		
        params.put ("title", "(?i).*" + title + ".*");
        params.put ("offset", offset);
        params.put ("limit", limit);
        
        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (d:Doc) WHERE d.title =~ $title RETURN d ORDER BY LOWER(d.title) ASC SKIP $offset LIMIT $limit";
        List <Doc> list = (List<Doc>) session.query(Doc.class, cypher, params);
        
        String totalQuery = "MATCH (d:Doc) WHERE d.title =~ $title RETURN count(*) AS total" ;
        Iterable<Map<String, Object>>  results =  session.query(totalQuery, params);
        
        long total = 0;
        
        for(Map<String, Object> row: results){
        	total = (long) row.get("total");
        }
           
        return new PageImpl<Doc>(list, pageable, total);
        
	}
}
