package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.*;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RAJobInterviewController extends Controller {

    /************************************************* Schedule Interview *************************************************/
    public Result scheduleInterview() {
        try {
            System.out.println("Scheduling RA Job Interview...");
            JsonNode json = request().body().asJson();
            if (json == null) {
                Logger.debug("RAJobInterviewController.scheduleInterview() - body is null");
                return Common.badRequestWrapper("Request body is empty.");
            }

            long applicationId = json.path("rajobApplicationId").asLong();
            System.out.println("ApplicationId: " + applicationId);

            RAJobApplication application = RAJobApplication.find.byId(applicationId);
            if (application == null) {
                Logger.debug("RAJobApplication not found with id: " + applicationId);
                return Common.badRequestWrapper("RA Job Application not found: " + applicationId);
            }

            List<RAJobInterview> existing = RAJobInterview.find.query()
                    .where().eq("rajobApplication.id", applicationId)
                    .ne("status", "cancelled")
                    .findList();
            if (!existing.isEmpty()) {
                Logger.debug("Active interview already exists for applicationId: " + applicationId);
                return Common.badRequestWrapper("An active interview is already scheduled for this application.");
            }

            RAJobInterview interview = new RAJobInterview();
            interview.setRajobApplication(application);
            interview.setInterviewDate(json.path("interviewDate").asText());
            interview.setInterviewTime(json.path("interviewTime").asText());
            interview.setLocation(json.path("location").asText());
            interview.setInterviewType(json.path("interviewType").asText());
            interview.setNotes(json.path("notes").asText(""));
            interview.setStatus("scheduled");
            interview.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            interview.save();

            System.out.println("Interview scheduled with id: " + interview.getId());
            return ok(Json.toJson(interview.getId()));
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.scheduleInterview() error: " + e.toString());
            e.printStackTrace();
            return internalServerError("Failed to schedule interview.");
        }
    }
    /************************************************* End of Schedule Interview ******************************************/


    /************************************************* Get Interview by Application ID ************************************/
    public Result getByApplicationId(Long applicationId) {
        try {
            RAJobInterview interview = RAJobInterview.find.query()
                    .where().eq("rajobApplication.id", applicationId)
                    .ne("status", "cancelled")
                    .findOne();
            if (interview == null) {
                return ok(Json.toJson(null));
            }
            return ok(Json.toJson(interview));
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.getByApplicationId() error: " + e.toString());
            return internalServerError("Failed to retrieve interview.");
        }
    }
    /************************************************* End of Get Interview by Application ID *****************************/


    /************************************************* Get Interview by ID ************************************************/
    public Result getById(Long interviewId) {
        try {
            RAJobInterview interview = RAJobInterview.find.byId(interviewId);
            if (interview == null) {
                Logger.debug("Interview not found with id: " + interviewId);
                return Common.badRequestWrapper("Interview not found: " + interviewId);
            }
            return ok(Json.toJson(interview));
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.getById() error: " + e.toString());
            return internalServerError("Failed to retrieve interview.");
        }
    }
    /************************************************* End of Get Interview by ID *****************************************/


    /************************************************* Get Interviews by Applicant ****************************************/
    public Result getByApplicant(Long userId) {
        try {
            List<RAJobInterview> interviews = RAJobInterview.find.query()
                    .where().eq("rajobApplication.applicant.id", userId)
                    .ne("status", "cancelled")
                    .findList();
            ArrayNode result = Json.newArray();
            for (RAJobInterview iv : interviews) {
                result.add(Json.toJson(iv));
            }
            return ok(result);
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.getByApplicant() error: " + e.toString());
            return internalServerError("Failed to retrieve interviews.");
        }
    }
    /************************************************* End of Get Interviews by Applicant *********************************/


    /************************************************* Get Interviews by Publisher ****************************************/
    public Result getByPublisher(Long userId) {
        try {
            List<RAJobInterview> interviews = RAJobInterview.find.query()
                    .where().eq("rajobApplication.appliedRAJob.rajobPublisher.id", userId)
                    .findList();
            ArrayNode result = Json.newArray();
            for (RAJobInterview iv : interviews) {
                result.add(Json.toJson(iv));
            }
            return ok(result);
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.getByPublisher() error: " + e.toString());
            return internalServerError("Failed to retrieve interviews.");
        }
    }
    /************************************************* End of Get Interviews by Publisher *********************************/


    /************************************************* Update Interview ***************************************************/
    public Result updateInterview(Long interviewId) {
        try {
            System.out.println("Updating RA Job Interview with id: " + interviewId);
            RAJobInterview interview = RAJobInterview.find.byId(interviewId);
            if (interview == null) {
                return Common.badRequestWrapper("Interview not found: " + interviewId);
            }
            JsonNode json = request().body().asJson();
            if (json == null) {
                return Common.badRequestWrapper("Request body is empty.");
            }
            if (json.has("interviewDate"))  interview.setInterviewDate(json.path("interviewDate").asText());
            if (json.has("interviewTime"))  interview.setInterviewTime(json.path("interviewTime").asText());
            if (json.has("location"))       interview.setLocation(json.path("location").asText());
            if (json.has("interviewType"))  interview.setInterviewType(json.path("interviewType").asText());
            if (json.has("notes"))          interview.setNotes(json.path("notes").asText());
            if (json.has("status"))         interview.setStatus(json.path("status").asText());
            interview.update();
            return ok(Json.toJson(interview.getId()));
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.updateInterview() error: " + e.toString());
            e.printStackTrace();
            return internalServerError("Failed to update interview.");
        }
    }
    /************************************************* End of Update Interview ********************************************/


    /************************************************* Cancel Interview ***************************************************/
    public Result cancelInterview(Long interviewId) {
        try {
            System.out.println("Cancelling interview with id: " + interviewId);
            RAJobInterview interview = RAJobInterview.find.byId(interviewId);
            if (interview == null) {
                Logger.debug("Interview not found with id: " + interviewId);
                return Common.badRequestWrapper("Interview not found: " + interviewId);
            }
            interview.setStatus("cancelled");
            interview.update();
            return ok(Json.toJson(true));
        } catch (Exception e) {
            Logger.error("RAJobInterviewController.cancelInterview() error: " + e.toString());
            return internalServerError("Failed to cancel interview.");
        }
    }
    /************************************************* End of Cancel Interview ********************************************/
}
