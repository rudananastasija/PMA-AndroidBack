package rs.ftn.pma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.pma.dto.RouteRequest;
import rs.ftn.pma.dto.RouteResponse;
import rs.ftn.pma.mappers.RouteMapper;
import rs.ftn.pma.model.Point;
import rs.ftn.pma.model.Route;
import rs.ftn.pma.model.User;
import rs.ftn.pma.repository.RouteRepository;
import rs.ftn.pma.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RouteService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RouteRepository routeRepository;

    public RouteResponse saveRoute(String username, RouteRequest request){
        User user = userRepository.findOneByUsername(username);
        Route route = RouteMapper.INSTANCE.mapRequestToRoute(request);
        route.setUser(user);
        route = routeRepository.save(route);
        route.setPoints(preparePoints(request.getPoints(), route));
        routeRepository.save(route);
        RouteResponse routeResponse = RouteMapper.INSTANCE.mapToResponse(route);
        return  routeResponse;
    }

    private Set<Point> preparePoints(HashMap<String, List<Double>> points, Route route){
        Set<Point> pointsSet = new HashSet<>();
        TreeMap<String, List<Double>> treeMap = new TreeMap<>(new DateComparator());
        treeMap.putAll(points);
        for(String key : treeMap.keySet()){
            Point point = new Point();
            point.setLat(treeMap.get(key).get(0));
            point.setLng(treeMap.get(key).get(1));
            point.setTime(LocalDateTime.parse(key));
            point.setRoute(route);
            pointsSet.add(point);
        }

        return  pointsSet;
    }

    private HashMap<String, List<Double>> parsePoints(Set<Point> points) {
        HashMap<String, List<Double>> retPoints = new HashMap<>();
        for(Point point: points) {
            retPoints.put(point.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), new ArrayList<>(Arrays.asList(point.getLat(), point.getLng())));
        }
        return retPoints;
    }

    public List<RouteResponse> getAllRoutesForUser(String username) {
        User user = userRepository.findOneByUsername(username);
        if(user == null) return new ArrayList<>();
        List<Route> routes = routeRepository.findRoutesOfUser(user);
        if(routes == null || routes.size() == 0) return new ArrayList<>();
        ArrayList<RouteResponse> retRoutes = new ArrayList<>();
        for(Route route: routes) {
            RouteResponse retRoute = RouteMapper.INSTANCE.mapToResponse(route);
            retRoute.setPoints(parsePoints(route.getPoints()));
            retRoutes.add(retRoute);
        }
        return retRoutes;
    }
}

class DateComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        LocalDateTime dateTime1 = LocalDateTime.parse(o1);
        LocalDateTime dateTime2 = LocalDateTime.parse(o2);
        return dateTime1.compareTo(dateTime2);
    }
}

