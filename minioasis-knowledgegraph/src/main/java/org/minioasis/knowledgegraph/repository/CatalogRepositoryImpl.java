package org.minioasis.knowledgegraph.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minioasis.knowledgegraph.domain.Catalog;
import org.minioasis.knowledgegraph.domain.criteria.CatalogCriteria;
import org.neo4j.ogm.session.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CatalogRepositoryImpl implements CatalogRepositoryCustom {

	private final Session session;

	public CatalogRepositoryImpl(Session session) {
		this.session = session;
	}
	
	public Page<Catalog> findByCriteria(CatalogCriteria criteria, Pageable pageable) {

		String name = criteria.getName();
		String classMark = criteria.getClassMark();
		long offset = pageable.getOffset();
		final int limit = pageable.getPageSize();
		
		Map<String, Object> params = new HashMap<>();
        params.put ("offset", offset);
        params.put ("limit", limit);
        
        StringBuilder cypher =  (new StringBuilder()).append("MATCH (c:Catalog) ");
        StringBuilder totalQuery = (new StringBuilder()).append("MATCH (c:Catalog) ");
        
        if(name != null && !name.isEmpty()) {

        	params.put ("name", "(?i).*" + name + ".*");	
            cypher.append("WHERE c.name =~ $name ") ;
           totalQuery.append("WHERE c.name =~ $name ");
            
        }
        
        if(classMark != null && !classMark.isEmpty()){
        	
        	params.put ("classMark", "(?i).*" + classMark + ".*");
        	cypher.append("AND c.classMark =~ $classMark ");
        	totalQuery.append("AND c.classMark =~ $classMark ");

        }
        
    	cypher.append("RETURN c ORDER BY LOWER(c.name) ASC SKIP $offset LIMIT $limit");
    	totalQuery.append("RETURN count(*) AS total");
        
        List <Catalog> list = (List<Catalog>) session.query(Catalog.class, cypher.toString(), params);
        Iterable<Map<String, Object>>  results =  session.query(totalQuery.toString(), params);
        
        long total = 0;
        
        for(Map<String, Object> row: results){
        	total = (long) row.get("total");
        }
           
        return new PageImpl<Catalog>(list, pageable, total);
        
	}
}

