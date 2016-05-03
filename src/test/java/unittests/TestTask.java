package unittests;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.kinneret.devops.server.dao.TaskDao;
import edu.kinneret.devops.server.rest.Task;
import io.dropwizard.jackson.Jackson;
import org.junit.Rule;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.rules.ExpectedException;

import java.io.File;


/**
 * Created by tsadok on 14/04/2015.
 */
public class TestTask {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void tryToDeleteNonExisitingTask()
    {
        TaskDao test = new TaskDao("testPath");
        exception.expect(RuntimeException.class);
        test.deleteTask("-1");
    }
    @Test
    public void serializesToJSON() throws Exception {
        final Task task = new Task(1, "description to test");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/task.json"), Task.class));

        assertThat(MAPPER.writeValueAsString(task)).isEqualTo(expected);
    }
}
