package msl.rpamonitoring.application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import msl.rpamonitoring.application.Dto.AddTeamMemberDto;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Service.AddTeamMemberService;

@RestController
@RequestMapping("/rpabot/team")
@CrossOrigin
public class AddTeamMemberController {
    
    @Autowired
    private AddTeamMemberService addTeamMemberService;

    @PostMapping("/add")
    public ResponseEntity<Users> addTeamMember(@RequestBody AddTeamMemberDto addTeamMemberDto) {
        Users newUser = addTeamMemberService.addTeamMember(addTeamMemberDto);
        return ResponseEntity.ok(newUser);
    }
    
}
