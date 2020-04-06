package org.minioasis.knowledgegraph.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Category {

	@Id @GeneratedValue
	Long id;
	
	private String classMark;
	private String name;
	
	@Relationship(type = "PARENT", direction = Relationship.OUTGOING)
	private Category parent;
	
	@Relationship(type = "PARENT", direction = Relationship.INCOMING)
	private List<Category> childrens = new ArrayList<Category>();
	
	@Relationship(type = "UNDER")
	private Set<Archive> archives = new HashSet<Archive>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassMark() {
		return classMark;
	}
	public void setClassMark(String classMark) {
		this.classMark = classMark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	// children
	public List<Category> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<Category> childrens) {
		this.childrens = childrens;
	}
	public void addChildren(Category children) {
		this.childrens.add(children);
	}
	public void removeChildren(Category children) {
		this.childrens.remove(children);
	}
	// archive
	public void addArchive(Archive archive) {
		this.archives.add(archive);
	}
	public Set<Archive> getArchives() {
		return archives;
	}
	public void setArchives(Set<Archive> archives) {
		this.archives = archives;
	}
	public void removeArchive(Archive archive) {
		this.archives.remove(archive);
	}
	
}
