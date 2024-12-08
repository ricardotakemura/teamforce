package br.net.ricardotakemura.teamforce.endpoint;

import java.util.List;

import br.net.ricardotakemura.teamforce.model.Task;
import br.net.ricardotakemura.teamforce.service.TaskService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/task")
public class TaskEndpoint {
	@Inject
	private TaskService taskService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
    	List<Task> result = taskService.getTasks();
    	return Response.ok(result).build();
    }

    @PATCH
    @Path("/{taskId}/worker/{workerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addWorkerInTask(@PathParam("taskId") Long taskId,
    		@PathParam("workerId") String workerId) {
    	Task result = taskService.addWorker(taskId, workerId);
    	return Response.ok(result).build();
    }

    @PATCH
    @Path("/{taskId}/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("taskId") Long taskId,
    		@PathParam("status") String status) {
    	Task result = taskService.updateStatus(taskId, status);
    	return Response.ok(result).build();
    }

    @DELETE
    @Path("/{taskId}/worker/{workerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeWorkerInTask(@PathParam("taskId") Long taskId,
    		@PathParam("workerId") String workerId) {
    	taskService.removeWorker(taskId, workerId);
    	return Response.noContent().build();
    }
  
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
    	Task result = taskService.create(task);
    	return Response.ok(result).build();
    }

}
