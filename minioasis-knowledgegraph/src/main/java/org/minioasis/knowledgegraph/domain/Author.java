package org.minioasis.knowledgegraph.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Author {

	@Id @GeneratedValue
    Long id;
	
	private String name;
	private String email;
	private String desp;
	private String website;
	
	@Relationship(type = "BY")
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
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
