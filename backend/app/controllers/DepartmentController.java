package controllers;

import com.google.inject.Inject;
import models.Department;
import models.rest.RESTResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.DepartmentService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class DepartmentController extends Controller {

    public static final String DEPARTMENT_DEFAULT_SORT_BY = "name";
    public static final String DEPARTMENT_DEFAULT_ORDER   = "asc";

    private final DepartmentService departmentService;

    @Inject
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Returns a paginated, sorted list of all departments.
     *
     * @return 200 with RESTResponse envelope, 404 if no departments found
     */
    public Result departmentList(Optional<Integer> pageLimit,
                                 Optional<Integer> offset,
                                 Optional<String>  sortBy,
                                 Optional<String>  order) {
        try {
            List<Department> departments = departmentService.buildAllDepartments();
            if (departments.isEmpty()) {
                return notFound("No departments found");
            }
            String sortField = sortBy.orElse(DEPARTMENT_DEFAULT_SORT_BY);
            String sortOrder = order.orElse(DEPARTMENT_DEFAULT_ORDER);
            RESTResponse response = departmentService.paginateAndSort(
                departments, offset, pageLimit, sortField, sortOrder);
            return ok(response.response());
        } catch (Exception e) {
            Logger.error("DepartmentController.departmentList exception: " + e.toString(), e);
            return internalServerError("Failed to retrieve department list");
        }
    }

    /**
     * Returns the detail profile for a single department including faculty and projects.
     *
     * @param departmentName URL-encoded department name from path
     * @return 200 with department JSON, 400 if name is blank, 404 if not found
     */
    public Result departmentDetail(String departmentName) {
        String decoded;
        try {
            decoded = URLDecoder.decode(departmentName, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return badRequest("Invalid department name encoding");
        }

        if (decoded == null || decoded.trim().isEmpty()) {
            return badRequest("Department name is required");
        }

        try {
            Department department = departmentService.buildDepartmentDetail(decoded);
            if (department == null) {
                return notFound("Department not found: " + decoded);
            }
            return ok(Json.toJson(department));
        } catch (Exception e) {
            Logger.error("DepartmentController.departmentDetail exception: " + e.toString(), e);
            return internalServerError("Failed to retrieve department detail");
        }
    }
}
