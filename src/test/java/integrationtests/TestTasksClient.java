package integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import edu.kinneret.devops.server.core.KinneretServerApplication;
import edu.kinneret.devops.server.core.KinneretServerConfiguration;
import edu.kinneret.devops.server.dao.TaskDao;
import edu.kinneret.devops.server.rest.Task;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tsadok on 05/05/2015.
 */
public class TestTasksClient{
    {

    }

    @ClassRule
    public static final DropwizardAppRule<KinneretServerConfiguration> RULE =
            new DropwizardAppRule<KinneretServerConfiguration>(KinneretServerApplication.class, ResourceHelpers.resourceFilePath("kinneret-server.yml"));

    @Test
    public void createOneTaskAndGetItAndDeleteIt() throws JsonProcessingException,IOException {
        Client client = new JerseyClientBuilder().build();
        String tasksResourceTarget = String.format("http://localhost:%d/tasks", RULE.getLocalPort());
        ObjectMapper om = new ObjectMapper();

        //POSTing a new Task
        Task taskToCreate = new Task(123,"blablabla");
        String taskAsString = om.writeValueAsString(taskToCreate);
        Response postResponse = client.target(tasksResourceTarget)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(taskAsString, MediaType.APPLICATION_JSON_TYPE));
        assertThat(postResponse.getStatus()).isEqualTo(201);

        String out = postResponse.readEntity(String.class).toString();
        Task taskAfterPost = om.readValue(out, Task.class);
        assertThat(taskToCreate.getDescription()).isEqualTo(taskAfterPost.getDescription());
        Long createdTaskId = taskAfterPost.getId();

        //GETing the task we've created
        Response getResponse = client.target(tasksResourceTarget + "/" + createdTaskId)
                .request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        assertThat(getResponse.getStatus()).isEqualTo(200);

        String o = getResponse.readEntity(String.class).toString();
        Task taskAfterGet = om.readValue(o, Task.class);
        assertThat(taskAfterPost.getDescription()).isEqualTo(taskAfterGet.getDescription());
        assertThat(taskAfterPost.getId()).isEqualTo(taskAfterGet.getId());

        //Delete the task we just created
        Response deletedResponse = client.target(tasksResourceTarget + "/" + createdTaskId)
                .request().accept(MediaType.APPLICATION_JSON_TYPE).delete();
        assertThat(deletedResponse.getStatus()).isEqualTo(200);

        String deletedStr = deletedResponse.readEntity(String.class).toString();
        Task deletedTask = om.readValue(deletedStr, Task.class);
        assertThat(deletedTask.getDescription()).isEqualTo(taskAfterGet.getDescription());
        assertThat(deletedTask.getId()).isEqualTo(taskAfterGet.getId());

        Response deletedResponse2 = client.target(tasksResourceTarget + "/" + createdTaskId)
                .request().accept(MediaType.APPLICATION_JSON_TYPE).delete();
        assertThat(deletedResponse2.getStatus()).isEqualTo(500);
    }
}
