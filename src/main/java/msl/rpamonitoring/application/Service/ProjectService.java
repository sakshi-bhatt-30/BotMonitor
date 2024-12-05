package msl.rpamonitoring.application.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import msl.rpamonitoring.application.Dto.OrganizationSpecificProjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import msl.rpamonitoring.application.Dto.AddProjectRequestDto;
import msl.rpamonitoring.application.Entity.Organization;
import msl.rpamonitoring.application.Entity.Project;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.OrganizationRepository;
import msl.rpamonitoring.application.Repository.ProjectRepository;
import msl.rpamonitoring.application.Repository.UserRepository;
import msl.rpamonitoring.application.ResponseDto.ProjectResponseDto;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public Project addProject(AddProjectRequestDto addProjectRequestDto){
        Organization organization = organizationRepository.findById(addProjectRequestDto.getOrgId())
        .orElseThrow(()->new RuntimeException("Organization not found"));

        Users user = userRepository.findById(addProjectRequestDto.getUserId()).
        orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(!Objects.equals(user.getOrganization().getId(), organization.getId())){
            throw new RuntimeException("User does not belong to the organization");
        }
        Project project = new Project();
        if(user.isAdmin()){
            project.setName(addProjectRequestDto.getName());
            project.setOrganization(organization);
            project.setCreatedAt(LocalDateTime.now());
            project.setUpdatedAt(LocalDateTime.now());
            project.setDescription(addProjectRequestDto.getDescription());
            project.setCreatedBy(user);

            project.getUsers().add(user);

            projectRepository.save(project);

            organization.getProjects().add(project);
        }
        return project;
    }

    public List<OrganizationSpecificProjects> getAllProjectsBasedOnOrgID(Long orgId) {

//        List<Project> organizationProjects=projectRepository.findByOrganizationId(orgId);



        return projectRepository.findByOrganizationId(orgId);
    }

    // public List<Project> getAllProjectBasedOnUserId(Long userId){
    //     return projectRepository.findAllProjectsByUserId(userId);
    // }

    public List<ProjectResponseDto> getAllProjectBasedOnUserId(Long userId) {
        List<Project> projects = projectRepository.findAllProjectsByUserId(userId);
        return projects.stream()
                .map(project -> new ProjectResponseDto(
                        project.getId(),
                        project.getName(),
                        project.getDescription()
                ))
                .collect(Collectors.toList());
    }




}
