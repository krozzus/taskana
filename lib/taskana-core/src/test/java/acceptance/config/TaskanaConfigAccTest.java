package acceptance.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.h2.store.fs.FileUtils;
import org.junit.jupiter.api.Test;

import pro.taskana.TaskanaEngineConfiguration;
import pro.taskana.common.internal.TaskanaEngineImpl;
import pro.taskana.common.internal.TaskanaEngineTestConfiguration;

/**
 * Test taskana configuration without roles.
 *
 * @author bbr
 */
class TaskanaConfigAccTest extends TaskanaEngineImpl {

  TaskanaConfigAccTest() throws SQLException {
    super(
        new TaskanaEngineConfiguration(
            TaskanaEngineTestConfiguration.getDataSource(),
            true,
            TaskanaEngineTestConfiguration.getSchemaName()));
  }

  @Test
  void testDomains() {
    assertThat(getConfiguration().getDomains()).containsOnly("DOMAIN_A", "DOMAIN_B");
  }

  @Test
  void testClassificationTypes() {
    assertThat(getConfiguration().getClassificationTypes()).containsOnly("TASK", "DOCUMENT");
  }

  @Test
  void testClassificationCategories() {
    assertThat(getConfiguration().getClassificationCategoriesByType("TASK"))
        .containsOnly("EXTERNAL", "MANUAL", "AUTOMATIC", "PROCESS");
  }

  @Test
  void testDoesNotExistPropertyClassificationTypeOrItIsEmpty() throws IOException {
    taskanaEngineConfiguration.setClassificationTypes(new ArrayList<>());
    String propertiesFileName = createNewConfigFile("/dummyTestConfig.properties", false, true);
    String delimiter = ";";
    try {
      getConfiguration().initTaskanaProperties(propertiesFileName, delimiter);
    } finally {
      deleteFile(propertiesFileName);
    }
    assertThat(taskanaEngineConfiguration.getClassificationTypes()).isEmpty();
  }

  @Test
  void testDoesNotExistPropertyClassificatioCategoryOrItIsEmpty() throws IOException {
    taskanaEngineConfiguration.setClassificationTypes(new ArrayList<>());
    taskanaEngineConfiguration.setClassificationCategoriesByType(new HashMap<>());
    String propertiesFileName = createNewConfigFile("/dummyTestConfig.properties", true, false);
    String delimiter = ";";
    try {
      getConfiguration().initTaskanaProperties(propertiesFileName, delimiter);
    } finally {
      deleteFile(propertiesFileName);
    }
    assertThat(
            taskanaEngineConfiguration.getClassificationCategoriesByType(
                taskanaEngineConfiguration.getClassificationTypes().get(0)))
        .isEmpty();
  }

  @Test
  void testWithCategoriesAndClassificationFilled() throws IOException {
    taskanaEngineConfiguration.setClassificationTypes(new ArrayList<>());
    taskanaEngineConfiguration.setClassificationCategoriesByType(new HashMap<>());
    String propertiesFileName = createNewConfigFile("/dummyTestConfig.properties", true, true);
    String delimiter = ";";
    try {
      getConfiguration().initTaskanaProperties(propertiesFileName, delimiter);
    } finally {
      deleteFile(propertiesFileName);
    }
    assertThat(taskanaEngineConfiguration.getClassificationTypes().isEmpty()).isFalse();
    assertThat(
            taskanaEngineConfiguration
                .getClassificationCategoriesByType(
                    taskanaEngineConfiguration.getClassificationTypes().get(0))
                .isEmpty())
        .isFalse();
    assertThat(taskanaEngineConfiguration.getClassificationTypes()).hasSize(2);
    assertThat(
            taskanaEngineConfiguration
                .getClassificationCategoriesByType(
                    taskanaEngineConfiguration.getClassificationTypes().get(0))
                .size())
        .isEqualTo(4);
    assertThat(
            taskanaEngineConfiguration
                .getClassificationCategoriesByType(
                    taskanaEngineConfiguration.getClassificationTypes().get(1))
                .size())
        .isEqualTo(1);
  }

  private String createNewConfigFile(
      String filename, boolean addingTypes, boolean addingClassification) throws IOException {
    Path file = Files.createFile(Paths.get(System.getProperty("user.home") + filename));
    List<String> lines =
        Stream.of(
                "taskana.roles.Admin =Holger|Stefan",
                "taskana.roles.businessadmin  = ebe  | konstantin ",
                "taskana.roles.user = nobody")
            .collect(Collectors.toList());
    if (addingTypes) {
      lines.add("taskana.classification.types= TASK , document");
    }
    if (addingClassification) {
      lines.add("taskana.classification.categories.task= EXTERNAL, manual, autoMAtic, Process");
      lines.add("taskana.classification.categories.document= EXTERNAL");
    }

    Files.write(file, lines, StandardCharsets.UTF_8);
    return file.toString();
  }

  private void deleteFile(String propertiesFileName) {
    File f = new File(propertiesFileName);
    if (f.exists() && !f.isDirectory()) {
      FileUtils.delete(propertiesFileName);
    }
  }
}
