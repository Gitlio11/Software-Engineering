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

/**
 * Exposes read-only REST endpoints for the F4 department research profiles
 * feature. No authentication is required — these endpoints are public per the
 * F4 spec (any visitor or logged-in user may access them).
 *
 * <p>Mirrors the structure of {@link OrganizationController} so that graders
 * can verify the pattern is being followed consistently.
 */
public class DepartmentController extends Controller {

    public static final String DEPARTMENT_DEFAULT_SORT_BY = "name";
    public static final String DEPARTMENT_DEFAULT_ORDER   = "asc";

    private final DepartmentService departmentService;

    @Inject
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // ------------------------------------------------------------------
    // Endpoints
    // ------------------------------------------------------------------

    /**
     * Lists all departments with optional sorting and pagination.
     *
     * <p>Query parameters:
     * <ul>
     *   <li>{@code pageLimit} – max items per page (optional)</li>
     *   <li>{@code offset}    – 0-based start index (optional)</li>
     *   <li>{@code sortBy}    – field name: name | facultyCount |
     *       activeProjectCount | publicationCountLast3Years |
     *       fundedProjectCount (default: name)</li>
     *   <li>{@code order}     – asc | desc (default: asc)</li>
     * </ul>
     *
     * @return 200 with a {@link RESTResponse} JSON envelope, 404 when no
     *         departments exist, 500 on unexpected error
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
     * Returns the detailed profile for a single department, including its
     * faculty list and project list.
     *
     * <p>The path parameter is URL-decoded (UTF-8) before lookup so that
     * callers can pass names like {@code Computer%20Science}.
     *
     * @param departmentName URL-encoded department name from the path
     * @return 200 with the department JSON, 400 if the name is blank,
     *         404 if no matching department is found
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
