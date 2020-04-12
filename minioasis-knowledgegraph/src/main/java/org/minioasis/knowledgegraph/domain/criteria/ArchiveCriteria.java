package org.minioasis.knowledgegraph.domain.criteria;

import java.time.LocalDate;

public class ArchiveCriteria {

	private String archiveNo;
	private String name;
	private String content;
	private LocalDate createdTo;
	private LocalDate createdFrom;
	private LocalDate updatedTo;
	private LocalDate updatedFrom;
	
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
	public LocalDate getCreatedTo() {
		return createdTo;
	}
	public void setCreatedTo(LocalDate createdTo) {
		this.createdTo = createdTo;
	}
	public LocalDate getCreatedFrom() {
		return createdFrom;
	}
	public void setCreatedFrom(LocalDate createdFrom) {
		this.createdFrom = createdFrom;
	}
	public LocalDate getUpdatedTo() {
		return updatedTo;
	}
	public void setUpdatedTo(LocalDate updatedTo) {
		this.updatedTo = updatedTo;
	}
	public LocalDate getUpdatedFrom() {
		return updatedFrom;
	}
	public void setUpdatedFrom(LocalDate updatedFrom) {
		this.updatedFrom = updatedFrom;
	}
	
}
