package msl.rpamonitoring.application.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import msl.rpamonitoring.application.Dto.AllProcessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import msl.rpamonitoring.application.Dto.ProcessDto;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.ProcessRepository;
import msl.rpamonitoring.application.Repository.ProjectRepository;
import msl.rpamonitoring.application.Repository.UserRepository;
import msl.rpamonitoring.application.Entity.Process;
import msl.rpamonitoring.application.Entity.Project;
@Service
public class ProcessService {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired    
    private ProjectRepository projectRepository;

     public Process addProcess(ProcessDto processDto) {


        Users user = userRepository.findById(processDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(processDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));        

   // checks if user belongs to thiis project or not
        if(!project.getUsers().contains(user)){
            throw new RuntimeException("User does not belongs to the project");
        }   
               Process process = new Process();
               process.setProcessName(processDto.getProcessName());
               process.setCreatedAt(LocalDateTime.now());
               process.setProject(project);
               process.setExecutionTime(processDto.getExecutionTime());
               process.setCreatedBy(user);
               process.setActive(true);
               process.setUpdatedAt(LocalDateTime.now());
               return processRepository.save(process);
    }

    // public List<?> getAllProcesses() {
    //     List<Process> allProcesses=processRepository.findAll();
    //     return allProcesses.stream().map(process -> {
    //         AllProcessDto AllProcesses=new AllProcessDto();
    //         AllProcesses.setProcessName(process.getProcessName());
    //         AllProcesses.setExecutionTime(process.getExecutionTime());
    //         AllProcesses.setProjectId(process.getProject().getId());
    //         AllProcesses.setUserId(process.getCreatedBy().getId());
    //         AllProcesses.setCreatedAt(process.getCreatedAt());
    //         AllProcesses.setCreatedBy(process.getCreatedBy().getFirstName()+" "+process.getCreatedBy().getLastName());
    //         AllProcesses.setActive(process.isActive());
    //         return AllProcesses;
    //     }).collect(Collectors.toList());
    // }

    public List<?> getProcessByProjectId(Long projectId) {
        List<Process> allProcesses=processRepository.findAllProcessesByProjectId(projectId);

        return allProcesses.stream().map(process -> {
            AllProcessDto AllProcesses=new AllProcessDto();
            AllProcesses.setProcessName(process.getProcessName());
            AllProcesses.setExecutionTime(process.getExecutionTime());
            AllProcesses.setProcessId(process.getId());
            AllProcesses.setUserId(process.getCreatedBy().getId());
            AllProcesses.setCreatedAt(process.getCreatedAt());
            AllProcesses.setCreatedBy(process.getCreatedBy().getFirstName()+" "+process.getCreatedBy().getLastName());
            AllProcesses.setActive(process.isActive());
            return AllProcesses;
        }).collect(Collectors.toList());
    }
}
