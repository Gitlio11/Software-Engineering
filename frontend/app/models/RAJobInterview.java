package models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = RAJobInterview.class)
public class RAJobInterview {

    private long id;
    private RAJobApplication rajobApplication;
    private String interviewDate;
    private String interviewTime;
    private String location;
    private String interviewType;
    private String notes;
    private String status;
    private String createdTime;

    public static RAJobInterview deserialize(JsonNode json) {
        if (json == null || json.isNull()) return null;
        RAJobInterview iv = new RAJobInterview();
        iv.setId(json.path("id").asLong());
        iv.setInterviewDate(json.path("interviewDate").asText(""));
        iv.setInterviewTime(json.path("interviewTime").asText(""));
        iv.setLocation(json.path("location").asText(""));
        iv.setInterviewType(json.path("interviewType").asText(""));
        iv.setNotes(json.path("notes").asText(""));
        iv.setStatus(json.path("status").asText(""));
        iv.setCreatedTime(json.path("createdTime").asText(""));

        JsonNode appNode = json.path("rajobApplication");
        if (!appNode.isMissingNode() && !appNode.isNull()) {
            RAJobApplication app = new RAJobApplication();
            app.setId(appNode.path("id").asLong());

            JsonNode rajobNode = appNode.path("appliedRAJob");
            if (!rajobNode.isMissingNode() && !rajobNode.isNull()) {
                RAJob rajob = new RAJob();
                rajob.setId(rajobNode.path("id").asLong());
                rajob.setTitle(rajobNode.path("title").asText(""));
                app.setAppliedRAJob(rajob);
            }

            JsonNode applicantNode = appNode.path("applicant");
            if (!applicantNode.isMissingNode() && !applicantNode.isNull()) {
                User applicant = new User();
                applicant.setId(applicantNode.path("id").asLong());
                applicant.setUserName(applicantNode.path("userName").asText(""));
                applicant.setEmail(applicantNode.path("email").asText(""));
                app.setApplicant(applicant);
            }

            iv.setRajobApplication(app);
        }
        return iv;
    }

    public static List<RAJobInterview> deserializeList(JsonNode json) {
        List<RAJobInterview> list = new ArrayList<>();
        if (json == null || !json.isArray()) return list;
        for (JsonNode node : json) {
            RAJobInterview iv = deserialize(node);
            if (iv != null) list.add(iv);
        }
        return list;
    }
}
