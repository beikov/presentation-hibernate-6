package org.hibernate.presentation.model;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "forums" )
public class Forum {
	@Id
	private Integer id;
	private String name;
	private Instant created;
	@OneToMany(mappedBy = "forum")
	private Set<Post> posts;

	protected Forum() {
	}

	public Forum(Integer id, String name) {
		this( id, name, Instant.now() );
	}

	public Forum(Integer id, String name, Instant created) {
		this.id = id;
		this.name = name;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}
}
