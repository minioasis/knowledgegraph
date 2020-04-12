package org.minioasis.knowledgegraph.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Doc {

	@Id @GeneratedValue
    Long id;
	
	private String title;
	private String link;
	private String summary;
	private String content;	
	
    private LocalDateTime created = LocalDateTime.now();
 
    private LocalDateTime updated;
	
	@Relationship(type = "BY", direction = Relationship.INCOMING)
	private List<Author> authors = new ArrayList<Author>();
	
	@Relationship(type = "RELATED_TO", direction = Relationship.UNDIRECTED)
	private List<Doc> docs = new ArrayList<Doc>();
	
	@Relationship(type = "UNDER", direction = Relationship.INCOMING)
	private List<Catalog> catalogs = new ArrayList<Catalog>();
	
	@Relationship(type = "TAGGED", direction = Relationship.INCOMING)
	private List<Tag> tags = new ArrayList<Tag>();
	
	@Relationship(type = "OF")
	private List<Archive> archives = new ArrayList<Archive>();
	
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	// author
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public void addAuthor(Author  author) {
		this.authors.add(author);
	}
	public void removeAuthor(Author author) {
		this.authors.remove(author);
	}
	//  catalog
	public void AddCatalog(Catalog catalog) {
		this.catalogs.add(category);
	}
	public void removeCatalog(Catalog catalog) {
		this.catalogs.remove(catalog);
	}
	public List<Catalog> getCatalogs() {
		return catalogs;
	}
	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}
	// tag
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	public void  removeTag(Tag tag) {
		this.tags.remove(tag);
	}
	// archive
	public List<Archive> getArchives() {
		return archives;
	}
	public void setArchives(List<Archive> archives) {
		this.archives = archives;
	}
	public void addArchive(Archive archive) {
		this.archives.add(archive);
	}
	public void removeArchive(Archive archive) {
		this.archives.remove(archive);
	}

}
