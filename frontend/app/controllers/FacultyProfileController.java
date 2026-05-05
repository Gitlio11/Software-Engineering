package controllers;

import actions.OperationLoggingAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.FacultyProfileService;

import javax.inject.Inject;

import static controllers.Application.checkLoginStatus;

public class FacultyProfileController extends Controller {

    @Inject
    Config config;

    private final FacultyProfileService facultyProfileService;

    @Inject
    public FacultyProfileController(FacultyProfileService facultyProfileService) {
        this.facultyProfileService = facultyProfileService;
    }

    // GET /faculty/profiles
    @With(OperationLoggingAction.class)
    public Result facultyProfiles() {
        checkLoginStatus();
        String userTypes = session("userTypes");
        JsonNode profiles = facultyProfileService.getAllFacultyProfiles();
        return ok(views.html.facultyProfiles.render(profiles, userTypes));
    }

    // GET /faculty/profileDetail/:userId
    @With(OperationLoggingAction.class)
    public Result facultyProfileDetail(Long userId) {
        checkLoginStatus();
        String userTypes = session("userTypes");
        JsonNode profile = facultyProfileService.getFacultyProfile(userId);
        if (profile == null || profile.isNull()) {
            return redirect(routes.FacultyProfileController.facultyProfiles());
        }
        return ok(views.html.facultyProfileDetail.render(profile, userTypes));
    }
}
