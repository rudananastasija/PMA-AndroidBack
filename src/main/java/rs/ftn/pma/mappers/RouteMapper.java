package rs.ftn.pma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import rs.ftn.pma.dto.RouteRequest;
import rs.ftn.pma.dto.RouteResponse;
import rs.ftn.pma.model.Point;
import rs.ftn.pma.model.Route;

import java.time.LocalDateTime;
import java.util.*;

@Mapper
public interface RouteMapper {
    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);

    @Mapping(target = "id",ignore = true)
    @Mapping(source = "startTime",target ="startTime", qualifiedByName = "datePrepare")
    @Mapping(source = "endTime",target ="endTime", qualifiedByName = "datePrepare")
    @Mapping(source = "points", target = "points", ignore = true)
    @Mapping(source = "distance", target = "distance")
    @Mapping(source = "calories", target = "calories")
    Route mapRequestToRoute(RouteRequest request);

    @Named("datePrepare")
    static LocalDateTime prepareDate(String date){
        return LocalDateTime.parse(date);
    }

    @Mapping(source = "points", target="points", ignore = true)
    RouteResponse mapToResponse(Route route);
}