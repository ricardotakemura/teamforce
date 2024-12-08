package br.net.ricardotakemura.teamforce.repository;

import java.util.List;

import br.net.ricardotakemura.teamforce.model.EntityBean;

import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

@Stateful
public class JPARepository<Entity extends EntityBean<ID>, ID> {
	@PersistenceContext(unitName = "primary")
	protected EntityManager entityManager;
	protected Class<Entity> entityClass;
	
	protected JPARepository(Class<Entity> entityClass) {
		this.entityClass = entityClass;
	}
	
	public Entity create(Entity entity) {
		Entity newEntity = entityManager.merge(entity);
		entity.setId(newEntity.getId());
		entityManager.persist(newEntity);
		return entity;
	}
	
	public Entity findById(ID id) {
		return entityManager.find(entityClass, id);
	}
	
	public List<Entity> findAll() {
		CriteriaQuery<Entity> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
		query.from(entityClass);
		return entityManager
				.createQuery(query)
				.getResultList();
	}
	
	public void detach(Entity entity) {
		if (entity != null) {
			entityManager.detach(entity);
		}
	}
}
