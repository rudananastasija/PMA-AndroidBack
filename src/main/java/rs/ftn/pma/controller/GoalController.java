package rs.ftn.pma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.pma.dto.GoalRequest;
import rs.ftn.pma.dto.GoalResponse;
import rs.ftn.pma.services.GoalService;
import rs.ftn.pma.utils.JwtUtil;

@RestController
@RequestMapping(value = "goals")
public class GoalController {

    @Autowired
    GoalService goalService;

    @Autowired
    JwtUtil jwtUtil;
    @GetMapping(value = "")
    public ResponseEntity<?> getGoals(@RequestHeader("Authorization") String token) {
        // remove `Bearer ` part from token
        String username = jwtUtil.extractUsername(token.substring(7));
        try {
            return new ResponseEntity<>(goalService.getAllGoalsForUser(username), HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "")
    public ResponseEntity<?> createGoal(@RequestBody GoalRequest goalRequest,@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));

        try {
            return new ResponseEntity<>(goalService.createGoal(username, goalRequest), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/updateGoal")
    public ResponseEntity<?> updateGoal(@RequestBody GoalResponse goalResponse,@RequestHeader("Authorization") String token){

        try {
            return new ResponseEntity<>(goalService.updateGoal(goalResponse.getCurrentValue(), goalResponse.getId(), goalResponse.getNotified()), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
