package org.minioasis.knowledgegraph.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Tag {

		@Id @GeneratedValue
	    Long id;
		
		private String name;
		
		@Relationship(type = "TAGGED")
		private List<Doc> docs = new ArrayList<Doc>();

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		// doc
		public List<Doc> getDocs() {
			return docs;
		}
		public void setDocs(List<Doc> docs) {
			this.docs = docs;
		}
		public void addDoc(Doc doc) {
			this.docs.add(doc);
		}
		public void removeDoc(Doc doc) {
			this.docs.remove(doc);
		}
}
