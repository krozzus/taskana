package acceptance.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import acceptance.AbstractAccTest;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import pro.taskana.common.api.exceptions.InvalidArgumentException;
import pro.taskana.common.api.exceptions.NotAuthorizedException;
import pro.taskana.common.internal.security.JaasExtension;
import pro.taskana.common.internal.security.WithAccessId;
import pro.taskana.task.api.TaskService;
import pro.taskana.task.api.exceptions.TaskCommentNotFoundException;
import pro.taskana.task.api.exceptions.TaskNotFoundException;
import pro.taskana.task.api.models.TaskComment;

@ExtendWith(JaasExtension.class)
public class DeleteTaskCommentAccTest extends AbstractAccTest {

  DeleteTaskCommentAccTest() {
    super();
  }

  @WithAccessId(user = "user_1_1", groups = "group_1")
  @Test
  void should_DeleteTaskComment_For_TaskCommentId()
      throws TaskCommentNotFoundException, NotAuthorizedException, TaskNotFoundException,
                 InvalidArgumentException {

    TaskService taskService = taskanaEngine.getTaskService();

    List<TaskComment> taskComments =
        taskService.getTaskComments("TKI:000000000000000000000000000000000001");
    assertThat(taskComments).hasSize(2);

    taskService.deleteTaskComment("TCI:000000000000000000000000000000000004");

    // make sure the task comment was deleted
    List<TaskComment> taskCommentsAfterDeletion =
        taskService.getTaskComments("TKI:000000000000000000000000000000000001");
    assertThat(taskCommentsAfterDeletion).hasSize(1);
  }

  @WithAccessId(user = "user_1_2", groups = "group_1")
  @Test
  void should_FailToDeleteTaskComment_When_UserHasNoAuthorization()
      throws NotAuthorizedException, TaskNotFoundException {

    TaskService taskService = taskanaEngine.getTaskService();

    List<TaskComment> taskComments =
        taskService.getTaskComments("TKI:000000000000000000000000000000000000");
    assertThat(taskComments).hasSize(3);

    ThrowingCallable lambda =
        () -> taskService.deleteTaskComment("TCI:000000000000000000000000000000000000");

    assertThatThrownBy(lambda).isInstanceOf(NotAuthorizedException.class);

    // make sure the task comment was not deleted
    List<TaskComment> taskCommentsAfterDeletion =
        taskService.getTaskComments("TKI:000000000000000000000000000000000000");
    assertThat(taskCommentsAfterDeletion).hasSize(3);
  }

  @WithAccessId(user = "admin")
  @WithAccessId(user = "taskadmin")
  @TestTemplate
  void should_DeleteTaskComment_When_NoExplicitPermissionsButUserIsInAdministrativeRole()
      throws NotAuthorizedException, TaskNotFoundException, TaskCommentNotFoundException,
                 InvalidArgumentException, SQLException {

    resetDb(false);

    TaskService taskService = taskanaEngine.getTaskService();

    List<TaskComment> taskComments =
        taskService.getTaskComments("TKI:000000000000000000000000000000000002");
    assertThat(taskComments).hasSize(2);

    taskService.deleteTaskComment("TCI:000000000000000000000000000000000006");

    // make sure the task comment was deleted
    List<TaskComment> taskCommentsAfterDeletion =
        taskService.getTaskComments("TKI:000000000000000000000000000000000002");
    assertThat(taskCommentsAfterDeletion).hasSize(1);
  }

  @WithAccessId(user = "user_1_1", groups = "group_1")
  @Test
  void should_FailToDeleteTaskComment_When_TaskCommentIdIsInvalid()
      throws NotAuthorizedException, TaskNotFoundException {

    TaskService taskService = taskanaEngine.getTaskService();

    List<TaskComment> taskComments =
        taskService.getTaskComments("TKI:000000000000000000000000000000000000");
    assertThat(taskComments).hasSize(3);

    assertThatThrownBy(() -> taskService.deleteTaskComment(""))
        .isInstanceOf(InvalidArgumentException.class);

    assertThatThrownBy(() -> taskService.deleteTaskComment(null))
        .isInstanceOf(InvalidArgumentException.class);

    // make sure that no task comment was deleted
    List<TaskComment> taskCommentsAfterDeletion =
        taskService.getTaskComments("TKI:000000000000000000000000000000000000");
    assertThat(taskCommentsAfterDeletion).hasSize(3);
  }

  @WithAccessId(user = "user_1_1", groups = "group_1")
  @Test
  void should_FailToDeleteTaskComment_When_TaskCommentIsNotExisting()
      throws NotAuthorizedException, TaskNotFoundException {

    TaskService taskService = taskanaEngine.getTaskService();

    List<TaskComment> taskComments =
        taskService.getTaskComments("TKI:000000000000000000000000000000000000");
    assertThat(taskComments).hasSize(3);

    ThrowingCallable lambda = () -> taskService.deleteTaskComment("non existing task comment id");
    assertThatThrownBy(lambda).isInstanceOf(TaskCommentNotFoundException.class);

    // make sure the task comment was not deleted
    List<TaskComment> taskCommentsAfterDeletion =
        taskService.getTaskComments("TKI:000000000000000000000000000000000000");
    assertThat(taskCommentsAfterDeletion).hasSize(3);
  }
}
