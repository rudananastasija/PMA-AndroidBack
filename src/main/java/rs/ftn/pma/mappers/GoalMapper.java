package rs.ftn.pma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import rs.ftn.pma.dto.GoalRequest;
import rs.ftn.pma.dto.GoalResponse;
import rs.ftn.pma.model.Goals;

import java.time.LocalDate;

@Mapper
public interface GoalMapper {
    GoalMapper INSTANCE = Mappers.getMapper(GoalMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dateTime", target = "localDateTime", qualifiedByName = "dateMapping")
    @Mapping(source = "goalValue", target = "goalValue")
    @Mapping(source = "goalKey", target = "goalKey")
    @Mapping(source = "currentValue", target = "currentValue")
    @Mapping(source = "notified", target = "notified")

    Goals mapToGoal(GoalRequest goalRequest);

    @Named("dateMapping")
    static LocalDate dateTimeToLocaleDateTime(String dateTime) {
        if(dateTime != null && !dateTime.isEmpty())
            return LocalDate.parse(dateTime);
        return LocalDate.now();
    }

    GoalResponse mapToResponse(Goals goal);

}
