package org.minioasis.knowledgegraph.domain;

import java.util.ArrayList;
import java.util.List;

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
	private List<Doc> docs = new ArrayList<Doc>();

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
