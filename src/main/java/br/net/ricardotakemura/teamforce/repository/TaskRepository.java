package br.net.ricardotakemura.teamforce.repository;

import java.util.Date;

import br.net.ricardotakemura.teamforce.model.Task;
import br.net.ricardotakemura.teamforce.model.Task.Status;
import br.net.ricardotakemura.teamforce.model.Worker;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TaskRepository extends JPARepository<Task, Long> {
	
	public TaskRepository() {
		super(Task.class);
	}
	
	public Task updateStatus(Long id, Status status) {
		Task task = entityManager.find(Task.class, id);
		task.setUpdateAt(new Date());
		task.setStatus(status);
		return entityManager.merge(task);
	}
	
	public Task addWorker(Long id, Worker worker) {
		Task task = entityManager.find(Task.class, id);
		task.getWorkers().add(worker);
		return entityManager.merge(task);
	}
	
	public Task removeWorker(Long id, Worker worker) {
		Task task = entityManager.find(Task.class, id);
		task.getWorkers().remove(worker);
		return entityManager.merge(task);
	}
}
