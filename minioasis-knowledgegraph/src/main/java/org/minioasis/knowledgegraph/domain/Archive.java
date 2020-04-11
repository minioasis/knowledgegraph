package org.minioasis.knowledgegraph.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Archive {

	@Id
	@GeneratedValue
	Long id;
	private String title;
	private String desp;
	
	@Relationship(type = "OF", direction = Relationship.INCOMING)
	private List<Doc> tags = new ArrayList<Doc>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public List<Doc> getTags() {
		return tags;
	}
	public void setTags(List<Doc> tags) {
		this.tags = tags;
	}
	
}
