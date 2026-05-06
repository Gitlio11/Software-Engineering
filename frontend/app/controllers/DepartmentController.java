package controllers;

import actions.OperationLoggingAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.typesafe.config.Config;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import utils.RESTfulCalls;

import javax.inject.Inject;

import static controllers.Application.checkLoginStatus;

public class DepartmentController extends Controller {

    @Inject
    Config config;

    // GET /department/departmentList
    @With(OperationLoggingAction.class)
    public Result departmentList() {
        checkLoginStatus();
        Long userId = 0L;
        String username = "";
        try { userId = Long.parseLong(session("id")); } catch (Exception e) { }
        try { username = session("username") != null ? session("username") : ""; } catch (Exception e) { }

        com.fasterxml.jackson.databind.node.ArrayNode depts = JsonNodeFactory.instance.arrayNode();
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config, "/department/departmentList");
            JsonNode result = RESTfulCalls.getAPI(url);
            if (result != null && result.has("items") && result.get("items").isArray()) {
                depts = (com.fasterxml.jackson.databind.node.ArrayNode) result.get("items");
            }
        } catch (Exception e) {
            Logger.debug("DepartmentController.departmentList() exception: " + e.toString());
        }

        return ok(views.html.departments.render(userId, username, depts));
    }

    // GET /department/departmentListData
    public Result departmentListData() {
        try {
            String url = RESTfulCalls.getBackendAPIUrl(config, "/department/departmentList");
            JsonNode result = RESTfulCalls.getAPI(url);
            if (result == null) return ok(Json.parse("[]"));
            JsonNode items = result.path("items");
            if (items.isMissingNode() || items.isNull()) return ok(Json.parse("[]"));
            return ok(items);
        } catch (Exception e) {
            Logger.debug("DepartmentController.departmentListData() exception: " + e.toString());
            return ok(Json.parse("[]"));
        }
    }

    // GET /department/departmentDetail/:departmentName
    @With(OperationLoggingAction.class)
    public Result departmentDetail(String departmentName) {
        checkLoginStatus();
        Long userId = 0L;
        String username = "";
        try { userId = Long.parseLong(session("id")); } catch (Exception e) { }
        try { username = session("username") != null ? session("username") : ""; } catch (Exception e) { }

        JsonNode dept = null;
        try {
            String encoded = java.net.URLEncoder.encode(departmentName, "UTF-8");
            String url = RESTfulCalls.getBackendAPIUrl(config, "/department/departmentDetail/" + encoded);
            dept = RESTfulCalls.getAPI(url);
        } catch (Exception e) {
            Logger.debug("DepartmentController.departmentDetail() exception: " + e.toString());
        }

        return ok(views.html.departmentDetail.render(userId, username, dept));
    }
}
