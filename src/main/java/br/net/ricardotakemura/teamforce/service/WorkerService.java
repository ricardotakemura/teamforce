package br.net.ricardotakemura.teamforce.service;

import br.net.ricardotakemura.teamforce.model.Worker;
import br.net.ricardotakemura.teamforce.repository.WorkerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class WorkerService {
	
	@Inject
	private WorkerRepository workerRepository;

	
	@Transactional(value = TxType.SUPPORTS)
	public Worker login(String id, String password) {
		Worker worker = workerRepository.findById(id);
		workerRepository.detach(worker);
		if (worker != null && worker.getPassword().equals(password)) {
			return worker;
		}
		return null;
	}
	
	@Transactional(value = TxType.REQUIRED)
	public Worker create(Worker worker) {
		Worker result = workerRepository.create(worker);
		workerRepository.detach(result);
		return result;
	}
}
