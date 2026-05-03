package controllers;

import actions.OperationLoggingAction;
import com.typesafe.config.Config;
import models.RAJobApplication;
import models.RAJobInterview;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.RAJobApplicationService;
import services.RAJobInterviewService;

import javax.inject.Inject;
import java.util.List;

import static controllers.Application.checkLoginStatus;

public class RAJobInterviewController extends Controller {

    @Inject
    Config config;

    private final RAJobInterviewService interviewService;
    private final RAJobApplicationService applicationService;
    private final FormFactory formFactory;

    @Inject
    public RAJobInterviewController(RAJobInterviewService interviewService,
                                    RAJobApplicationService applicationService,
                                    FormFactory formFactory) {
        this.interviewService = interviewService;
        this.applicationService = applicationService;
        this.formFactory = formFactory;
    }

    // GET /rajobInterview/schedulePage/:rajobApplicationId
    @With(OperationLoggingAction.class)
    public Result scheduleInterviewPage(Long rajobApplicationId) {
        checkLoginStatus();
        String userTypes = session("userTypes");
        if (!"1".equals(userTypes) && !"0".equals(userTypes)) {
            return redirect(routes.Application.index());
        }

        RAJobApplication application = applicationService.getRAJobApplicationById(rajobApplicationId);
        if (application == null) {
            return redirect(routes.RAJobController.rajobList(1, ""));
        }

        RAJobInterview existing = interviewService.getInterviewByApplicationId(rajobApplicationId);
        return ok(views.html.rajobInterviewSchedule.render(application, existing, userTypes));
    }

    // POST /rajobInterview/schedulePOST/:rajobApplicationId
    public Result scheduleInterviewPOST(Long rajobApplicationId) {
        checkLoginStatus();
        String userTypes = session("userTypes");
        if (!"1".equals(userTypes) && !"0".equals(userTypes)) {
            return redirect(routes.Application.index());
        }

        DynamicForm form = formFactory.form().bindFromRequest();
        String date     = form.get("interviewDate");
        String time     = form.get("interviewTime");
        String location = form.get("location");
        String type     = form.get("interviewType");
        String notes    = form.get("notes");

        Long interviewId = interviewService.scheduleInterview(
                rajobApplicationId, date, time, location, type, notes);

        if (interviewId == null) {
            RAJobApplication application = applicationService.getRAJobApplicationById(rajobApplicationId);
            RAJobInterview existing = interviewService.getInterviewByApplicationId(rajobApplicationId);
            flash("error", "Could not schedule interview. An interview may already be scheduled.");
            return ok(views.html.rajobInterviewSchedule.render(application, existing, userTypes));
        }

        return redirect(routes.RAJobInterviewController.interviewDetail(interviewId));
    }

    // GET /rajobInterview/detail/:interviewId
    @With(OperationLoggingAction.class)
    public Result interviewDetail(Long interviewId) {
        checkLoginStatus();
        String userTypes = session("userTypes");

        RAJobInterview interview = interviewService.getInterviewById(interviewId);
        if (interview == null) {
            return redirect(routes.RAJobController.rajobList(1, ""));
        }
        return ok(views.html.rajobInterviewDetail.render(interview, userTypes));
    }

    // GET /rajobInterview/myInterviews
    @With(OperationLoggingAction.class)
    public Result myInterviews() {
        checkLoginStatus();
        String userTypes = session("userTypes");
        Long userId = Long.parseLong(session("id"));

        List<RAJobInterview> interviews;
        if ("1".equals(userTypes) || "0".equals(userTypes)) {
            interviews = interviewService.getInterviewsByPublisher(userId);
        } else {
            interviews = interviewService.getInterviewsByApplicant(userId);
        }
        return ok(views.html.rajobMyInterviews.render(interviews, userTypes));
    }

    // GET /rajobInterview/cancel/:interviewId
    public Result cancelInterview(Long interviewId) {
        checkLoginStatus();
        String userTypes = session("userTypes");
        if (!"1".equals(userTypes) && !"0".equals(userTypes)) {
            return redirect(routes.Application.index());
        }
        interviewService.cancelInterview(interviewId);
        return redirect(routes.RAJobInterviewController.myInterviews());
    }
}
