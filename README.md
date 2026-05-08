# SMU Scientific Hub

This portal aims to provide an open platform that supports and facilitates collaborations between SMU and local industry. Industry can publish a challenge, and our platform will recommend SMU researcher(s), based on AI, Machine Learning, Deep Learning, Natural Language Processing, Knowledge Graph, and Data Mining techniques.

## Project Overview
This is a frontend-backend separated project, with each running on different ports.

- Frontend Port: `9038`
- Backend Port: `9039`

### How to Run the Project
To run the project, open both the backend and frontend projects and manage them using the sbt shell.

Steps
1. Open the project with the `build.sbt` file.

2. In the sbt shell, enter the following command:

```bash
run
```

This will start the frontend and backend on their respective ports. You can access the landing page by navigating to `http://localhost:9038`.

## F4: Department Research Profiles

Browse and explore the research activity of each academic department at SMU.

### Viewing the Department List

Navigate to `/departments` in the frontend to see all departments with:

- Number of faculty members
- Number of active research projects
- Number of funded projects
- Publications in the last 3 years
- Top research keywords

Use the sort and filter controls to order departments by faculty count, publications, or name. The search bar lets you narrow by department name.

### Viewing a Department Detail Page

Click any department card to open its detail page (`/departments/:name`), which shows:

- Full faculty roster with links to individual profiles
- All projects led by faculty in that department (active and inactive)
- An interactive keyword cloud of the department's research interests

### API Access (Developers)

The backend exposes two public REST endpoints — no authentication required:

```
GET /department/departmentList?sortBy=name&order=asc&pageLimit=10&offset=0
GET /department/departmentDetail/Computer%20Science
```

See `docs/features/F4.md` for full request/response documentation.

---

## Contribution Guidelines
For contributors, please follow these guidelines when making changes to the code:

- Create a new branch named after yourself.
- Make modifications in your own branch.
- Submit your changes via a pull request for review and merging.

By following this process, we ensure that all code changes are tracked and reviewed properly before being merged into the main project.

