package br.net.ricardotakemura.teamforce.service;

import java.util.Date;
import java.util.List;

import br.net.ricardotakemura.teamforce.model.Task;
import br.net.ricardotakemura.teamforce.model.Task.Status;
import br.net.ricardotakemura.teamforce.model.Worker;
import br.net.ricardotakemura.teamforce.repository.TaskRepository;
import br.net.ricardotakemura.teamforce.repository.WorkerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class TaskService {
	@Inject	
	private TaskRepository taskRepository;
	@Inject
	private WorkerRepository workerRepository;
	
	@Transactional(value = TxType.REQUIRES_NEW)
	public Task create(Task task) {
		Date now = new Date();
		task.setStatus(Status.OPEN);
		task.setCreateAt(now);
		task.setUpdateAt(now);
		Task result = taskRepository.create(task);
		result.getWorkers().size();
		taskRepository.detach(result);
		return result;
	}
	
	@Transactional(value = TxType.REQUIRES_NEW)
	public Task updateStatus(Long id, String status) {
		Task result = taskRepository.updateStatus(id, Status.valueOf(status));
		result.getWorkers().size();
		taskRepository.detach(result);
		return result;
	}
	
	@Transactional(value = TxType.REQUIRES_NEW)
	public Task addWorker(Long taskId, String workerId) {
		Worker worker = workerRepository.findById(workerId);
		Task result = taskRepository.addWorker(taskId, worker);
		result.getWorkers().size();
		taskRepository.detach(result);
		return result;
	}
	
	@Transactional(value = TxType.REQUIRES_NEW)
	public Task removeWorker(Long taskId, String workerId) {
		Worker worker = workerRepository.findById(workerId);
		Task result = taskRepository.removeWorker(taskId, worker);
		result.getWorkers().size();
		taskRepository.detach(result);
		return result;
	}
	
	@Transactional(value = TxType.SUPPORTS)
	public List<Task> getTasks() {
		List<Task> result = taskRepository.findAll();
		result.forEach(item -> { 
			item.getWorkers().size();
			taskRepository.detach(item);
		});
		return result;
	}
}
