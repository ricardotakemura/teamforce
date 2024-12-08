package br.net.ricardotakemura.teamforce.repository;

import java.util.Date;

import br.net.ricardotakemura.teamforce.model.Task;
import br.net.ricardotakemura.teamforce.model.Task.Status;
import br.net.ricardotakemura.teamforce.model.Worker;
import br.net.ricardotakemura.teamforce.repository.JPARepository;
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
		entityManager.merge(task);
		entityManager.flush();
		return task;
	}
	
	public Task updateWorker(Long id, Worker worker) {
		Task task = entityManager.find(Task.class, id);
		task.setWorker(worker);
		entityManager.merge(task);
		entityManager.flush();
		return task;
	}
	
	public Task removeWorker(Long id, Worker worker) {
		Task task = entityManager.find(Task.class, id);
		task.setWorker(null);
		entityManager.merge(task);
		entityManager.flush();
		return task;
	}
}
