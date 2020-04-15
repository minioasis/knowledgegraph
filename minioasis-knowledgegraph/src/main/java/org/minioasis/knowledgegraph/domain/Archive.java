package org.minioasis.knowledgegraph.domain;

import java.time.LocalDateTime;
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
	private String name;
	private String archiveNo;
	private String content;
	private LocalDateTime created;
	private LocalDateTime updated;
	
	@Relationship(type = "BELONG_TO", direction = Relationship.INCOMING)
	private List<Doc> docs = new ArrayList<Doc>();
	
	@Relationship(type = "CHILD_OF", direction = Relationship.INCOMING)
	private List<Archive> childrens = new ArrayList<Archive>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getArchiveNo() {
		return archiveNo;
	}
	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getUpdated() {
		return updated;
	}
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
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
	// archive
	public void addChildren(Archive archive) {
		this.childrens.add(archive);
	}
	public List<Archive> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<Archive> childrens) {
		this.childrens = childrens;
	}
	public void removeChildren(Archive archive) {
		this.childrens.remove(archive);
	}	
	
}
