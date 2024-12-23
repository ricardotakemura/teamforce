package br.net.ricardotakemura.teamforce.model;

import java.util.Date;
import java.util.Objects;

import br.net.ricardotakemura.teamforce.model.EntityBean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "task")
public class Task implements EntityBean<Long> {
	public enum Status {
		OPEN,
		WORK,
		CODE_REVIEW,
		TEST,
		DEPLOY,
		CLOSE,
	}
	
	public enum Type {
		ANALYSIS,
		FEATURE,
		BUG
	}
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	@Column(name = "update_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne(targetEntity = Worker.class, 
			optional = true,
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	private Worker worker;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createAt, description, id, status, type, updateAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(createAt, other.createAt) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && status == other.status && type == other.type
				&& Objects.equals(updateAt, other.updateAt);
	}
}
