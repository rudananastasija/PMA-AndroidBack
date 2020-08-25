package rs.ftn.pma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.pma.dto.GoalRequest;
import rs.ftn.pma.dto.GoalResponse;
import rs.ftn.pma.dto.RouteResponse;
import rs.ftn.pma.mappers.GoalMapper;
import rs.ftn.pma.mappers.RouteMapper;
import rs.ftn.pma.model.Goals;
import rs.ftn.pma.model.Route;
import rs.ftn.pma.model.User;
import rs.ftn.pma.repository.GoalRepository;
import rs.ftn.pma.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    UserRepository userRepository;

    public GoalResponse createGoal(String username, GoalRequest goal) {
        User user = userRepository.findOneByUsername(username);
        Goals newGoal = GoalMapper.INSTANCE.mapToGoal(goal);
        newGoal.setUser(user);
        newGoal = goalRepository.save(newGoal);
        System.out.println("dosao u service");
        return GoalMapper.INSTANCE.mapToResponse(newGoal);
    }
    public GoalResponse updateGoal(Double value, Long id,int notifiedFlag) {

        Goals goal = goalRepository.findOneById(id);
        goal.setCurrentValue(value);
        goal.setNotified(notifiedFlag);
        Goals savedGoal = goalRepository.save(goal);
        return GoalMapper.INSTANCE.mapToResponse(savedGoal);

    }

    public List<GoalResponse> getAllGoalsForUser(String username) {
        User user = userRepository.findOneByUsername(username);
        if(user == null) return new ArrayList<>();
        List<Goals> goals = goalRepository.findGoalsOfUser(user);
        if(goals == null || goals.size() == 0) return new ArrayList<>();
        ArrayList<GoalResponse> retGoals = new ArrayList<>();
        for(Goals goal: goals) {
            GoalResponse goalRes = GoalMapper.INSTANCE.mapToResponse(goal);
            retGoals.add(goalRes);
        }
        return retGoals;
    }

}
