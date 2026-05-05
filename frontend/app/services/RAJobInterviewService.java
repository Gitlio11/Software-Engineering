package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import models.RAJobInterview;
import play.Logger;
import play.libs.Json;
import utils.Constants;
import utils.RESTfulCalls;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RAJobInterviewService {

    @Inject
    Config config;

    public RAJobInterview getInterviewByApplicationId(Long applicationId) {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config,
                    Constants.GET_INTERVIEW_BY_APPLICATION_ID + applicationId);
            JsonNode response = RESTfulCalls.getAPI(url);
            if (response == null || response.isNull()) return null;
            return RAJobInterview.deserialize(response);
        } catch (Exception e) {
            Logger.error("RAJobInterviewService.getInterviewByApplicationId() error: " + e.toString());
            return null;
        }
    }

    public RAJobInterview getInterviewById(Long interviewId) {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config,
                    Constants.GET_INTERVIEW_BY_ID + interviewId);
            JsonNode response = RESTfulCalls.getAPI(url);
            if (response == null || response.isNull()) return null;
            return RAJobInterview.deserialize(response);
        } catch (Exception e) {
            Logger.error("RAJobInterviewService.getInterviewById() error: " + e.toString());
            return null;
        }
    }

    public List<RAJobInterview> getInterviewsByApplicant(Long userId) {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config,
                    Constants.GET_INTERVIEWS_BY_APPLICANT + userId);
            JsonNode response = RESTfulCalls.getAPI(url);
            return RAJobInterview.deserializeList(response);
        } catch (Exception e) {
            Logger.error("RAJobInterviewService.getInterviewsByApplicant() error: " + e.toString());
            return new ArrayList<>();
        }
    }

    public List<RAJobInterview> getInterviewsByPublisher(Long userId) {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config,
                    Constants.GET_INTERVIEWS_BY_PUBLISHER + userId);
            JsonNode response = RESTfulCalls.getAPI(url);
            return RAJobInterview.deserializeList(response);
        } catch (Exception e) {
            Logger.error("RAJobInterviewService.getInterviewsByPublisher() error: " + e.toString());
            return new ArrayList<>();
        }
    }

    public Long scheduleInterview(long applicationId, String date, String time,
                                  String location, String type, String notes) {
        try {
            ObjectNode body = Json.newObject();
            body.put("rajobApplicationId", applicationId);
            body.put("interviewDate", date);
            body.put("interviewTime", time);
            body.put("location", location);
            body.put("interviewType", type);
            body.put("notes", notes != null ? notes : "");

            String url = RESTfulCalls.getBackendAPIUrl(config, Constants.SCHEDULE_INTERVIEW);
            JsonNode response = RESTfulCalls.postAPI(url, body);
            if (response == null || response.has("error")) return null;
            return response.asLong();
        } catch (Exception e) {
            Logger.error("RAJobInterviewService.scheduleInterview() error: " + e.toString());
            return null;
        }
    }

    public boolean cancelInterview(Long interviewId) {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config,
                    Constants.CANCEL_INTERVIEW + interviewId);
            JsonNode response = RESTfulCalls.getAPI(url);
            return response != null && response.asBoolean();
        } catch (Exception e) {
            Logger.error("RAJobInterviewService.cancelInterview() error: " + e.toString());
            return false;
        }
    }
}
