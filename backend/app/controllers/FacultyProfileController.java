package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ResearcherInfo;
import models.User;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class FacultyProfileController extends Controller {

    // GET /faculty/profiles
    public Result getAllFacultyProfiles() {
        try {
            List<ResearcherInfo> profiles = ResearcherInfo.find.query()
                    .fetch("user")
                    .where().eq("user.userType", 1)
                    .findList();

            ArrayNode result = Json.newArray();
            for (ResearcherInfo ri : profiles) {
                ObjectNode node = buildProfileNode(ri);
                result.add(node);
            }
            return ok(result);
        } catch (Exception e) {
            Logger.error("FacultyProfileController.getAllFacultyProfiles() error: " + e.toString());
            return internalServerError("Failed to retrieve faculty profiles.");
        }
    }

    // GET /faculty/profile/:userId
    public Result getFacultyProfile(Long userId) {
        try {
            User user = User.find.byId(userId);
            if (user == null || user.getUserType() != 1) {
                return badRequest(Json.newObject().put("error", "Faculty not found: " + userId));
            }

            ResearcherInfo ri = ResearcherInfo.find.query()
                    .fetch("user")
                    .where().eq("user.id", userId)
                    .findOne();

            if (ri == null) {
                ObjectNode node = Json.newObject();
                node.put("userId", userId);
                node.put("userName", user.getUserName());
                node.put("email", user.getEmail());
                node.putNull("highestDegree");
                node.putNull("orcid");
                node.putNull("researchFields");
                node.putNull("school");
                node.putNull("department");
                return ok(node);
            }

            return ok(buildProfileNode(ri));
        } catch (Exception e) {
            Logger.error("FacultyProfileController.getFacultyProfile() error: " + e.toString());
            return internalServerError("Failed to retrieve faculty profile.");
        }
    }

    private ObjectNode buildProfileNode(ResearcherInfo ri) {
        ObjectNode node = Json.newObject();
        User u = ri.getUser();
        node.put("userId", u != null ? u.getId() : 0L);
        node.put("userName", u != null ? u.getUserName() : "");
        node.put("email", u != null ? u.getEmail() : "");
        node.put("highestDegree", ri.getHighestDegree() != null ? ri.getHighestDegree() : "");
        node.put("orcid", ri.getOrcid() != null ? ri.getOrcid() : "");
        node.put("researchFields", ri.getResearchFields() != null ? ri.getResearchFields() : "");
        node.put("school", ri.getSchool() != null ? ri.getSchool() : "");
        node.put("department", ri.getDepartment() != null ? ri.getDepartment() : "");
        return node;
    }
}
