package pro.taskana.task.rest.models;

import org.springframework.hateoas.RepresentationModel;

import pro.taskana.classification.rest.models.ClassificationSummaryRepresentationModel;
import pro.taskana.task.api.models.ObjectReference;

public class AttachmentSummaryRepresentationModelZIEL
    extends RepresentationModel<AttachmentSummaryRepresentationModelZIEL> {
  public String attachmentId;
  public String taskId;
  public String created;
  public String modified;
  public ClassificationSummaryRepresentationModel classificationSummary;
  public ObjectReference objectReference;
  public String channel;
  public String received;
}
