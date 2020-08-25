package rs.ftn.pma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.pma.dto.RouteRequest;
import rs.ftn.pma.services.RouteService;
import rs.ftn.pma.utils.JwtUtil;

import java.util.ArrayList;

@RestController
@RequestMapping(value ="routes")
public class RouteController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RouteService routeService;

    // consider adding later -->`pageable`<-- here
    @GetMapping(value = "")
    public ResponseEntity<?> getRoutes(@RequestHeader("Authorization") String token) {
        // remove `Bearer ` part from token
        String username = jwtUtil.extractUsername(token.substring(7));
        return new ResponseEntity<>(routeService.getAllRoutesForUser(username),HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> saveRoute(@RequestBody RouteRequest request,@RequestHeader("Authorization") String token){
        String username = jwtUtil.extractUsername(token.substring(7));
        return ResponseEntity.ok(routeService.saveRoute(username,request));
    }
}
