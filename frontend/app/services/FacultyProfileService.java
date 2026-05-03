package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import play.Logger;
import play.libs.Json;
import utils.Constants;
import utils.RESTfulCalls;

import javax.inject.Inject;

public class FacultyProfileService {

    @Inject
    Config config;

    public JsonNode getAllFacultyProfiles() {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config, Constants.GET_ALL_FACULTY_PROFILES);
            JsonNode response = RESTfulCalls.getAPI(url);
            return response != null ? response : Json.newArray();
        } catch (Exception e) {
            Logger.error("FacultyProfileService.getAllFacultyProfiles() error: " + e.toString());
            return Json.newArray();
        }
    }

    public JsonNode getFacultyProfile(Long userId) {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config,
                    Constants.GET_FACULTY_PROFILE + userId);
            JsonNode response = RESTfulCalls.getAPI(url);
            return response;
        } catch (Exception e) {
            Logger.error("FacultyProfileService.getFacultyProfile() error: " + e.toString());
            return null;
        }
    }
}
