package br.net.ricardotakemura.teamforce.endpoint;


import java.net.URI;
import java.util.List;

import br.net.ricardotakemura.teamforce.model.Worker;
import br.net.ricardotakemura.teamforce.service.WorkerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/worker")
public class WorkerEndpoint {
	@Inject
	private WorkerService workerService;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("user") String id, 
    		@QueryParam("passwd") String password) {
    	Worker result = workerService.login(id, password);
    	if (result != null) {
    		return Response.ok(result).build();
    	}
    	return Response.status(Status.FORBIDDEN).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkers() {
    	List<Worker> workers = workerService.getWorkers();
    	return Response.ok(workers).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Worker worker) {
    	Worker result = workerService.create(worker);
    	return Response.created(
    		URI.create("/worker/login?id=" + result.getId() + "&passwd=" + result.getPassword())
    	).build();
    }
}
