package msl.rpamonitoring.application.Controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import msl.rpamonitoring.application.Dto.ProcessDto;
import msl.rpamonitoring.application.Service.ProcessService;
import msl.rpamonitoring.application.Entity.Process;

@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessController {
    @Autowired
    private ProcessService processService;

     @PostMapping("/add")
    public ResponseEntity<Process> addProcess(@RequestBody ProcessDto processDto){
            log.info("Trying to add Process");
            Process newProcess = processService.addProcess(processDto);
            return new ResponseEntity<>(newProcess,HttpStatus.CREATED);
    }

    // Need to change this.
    // @GetMapping("/all")
    // public ResponseEntity<List<?>> getAllProcesses() {
    //     log.info("Fetching All Process");
    //     List<?> processes = processService.getAllProcesses();
    //     return new ResponseEntity<>(processes, HttpStatus.OK);
    // }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<?>> getProcessesByProjectId(@PathVariable Long projectId) {
        log.info("Fetching Processes for Project ID: " + projectId);
        List<?> processes = processService.getProcessByProjectId(projectId);
        return new ResponseEntity<>(processes, HttpStatus.OK);
    }
}
