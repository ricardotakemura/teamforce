package br.net.ricardotakemura.teamforce.repository;

import br.net.ricardotakemura.teamforce.model.Worker;
import br.net.ricardotakemura.teamforce.repository.JPARepository;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class WorkerRepository extends JPARepository<Worker, String> {
	public WorkerRepository() {
		super(Worker.class);
	}
}
