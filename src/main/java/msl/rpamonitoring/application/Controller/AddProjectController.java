package msl.rpamonitoring.application.Controller;

import msl.rpamonitoring.application.Dto.OrganizationSpecificProjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import msl.rpamonitoring.application.Dto.AddProjectRequestDto;
import msl.rpamonitoring.application.Entity.Project;
import msl.rpamonitoring.application.ResponseDto.ProjectResponseDto;
import msl.rpamonitoring.application.Service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/rpabot/project")
public class AddProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping("/add")
    public ResponseEntity<Project> addProject(@RequestBody AddProjectRequestDto addProjectRequestDto) {
        Project project = projectService.addProject(addProjectRequestDto);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<OrganizationSpecificProjects>> getProjectsByOrganization(@PathVariable Long orgId) {
        List<OrganizationSpecificProjects> projects = projectService.getAllProjectsBasedOnOrgID(orgId);
        return ResponseEntity.ok(projects);
    }

     @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByUserId(@PathVariable Long userId) {
        List<ProjectResponseDto> projects = projectService.getAllProjectBasedOnUserId(userId);
        return ResponseEntity.ok(projects);
    }
}
