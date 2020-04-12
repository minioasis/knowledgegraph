package org.minioasis.knowledgegraph.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Catalog {

	@Id @GeneratedValue
	Long id;
	
	private String classMark;
	private String name;
	
	@Relationship(type = "PARENT", direction = Relationship.OUTGOING)
	private Catalog parent;
	
	@Relationship(type = "PARENT", direction = Relationship.INCOMING)
	private List<Catalog> childrens = new ArrayList<Catalog>();
	
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
	public Catalog getParent() {
		return parent;
	}
	public void setParent(Catalog parent) {
		this.parent = parent;
	}
	// children
	public List<Catalog> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<Catalog> childrens) {
		this.childrens = childrens;
	}
	public void addChildren(Catalog children) {
		this.childrens.add(children);
	}
	public void removeChildren(Catalog children) {
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
