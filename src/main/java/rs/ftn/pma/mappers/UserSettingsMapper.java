package rs.ftn.pma.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import rs.ftn.pma.dto.*;
import rs.ftn.pma.model.User;
import rs.ftn.pma.model.UserSettings;

@Mapper
public interface UserSettingsMapper {
    UserSettingsMapper INSTANCE = Mappers.getMapper(UserSettingsMapper.class);

    @Mapping(target = "id", ignore = true)
    UserSettings mapToUserSettings(UserSettingRequest userSettingRequest);

    @Mapping(source = "user", target = "userId", qualifiedByName = "mapUserId")
    UserSettingResponse mapToUserSettingsResponse(UserSettings userSettings);

    @Mapping(source = "waterReminder", target = "waterReminder")
    SettingsResponse mapToSettingsResponse(UserSettings userSettings);

    @Named("mapUserId")
    static Long mapUserId(User user) {
        return user.getId();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
//    @Mapping(target = "height", source = "height")
//    @Mapping(target = "weight", source = "weight")
//    @Mapping(target = "sex", source="sex")
//    @Mapping(target = "waterReminder", source = "waterReminder")
    void patchMapping(@MappingTarget UserSettings userSettings, UserSettingRequest userSettingRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "height", source = "height")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "sex", source="gender")
    void patchMappingSettings(@MappingTarget UserSettings userSettings, UserProfileResponse profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "waterReminder", source = "waterReminder")
    void patchMappingSettingsReminder(@MappingTarget UserSettings userSettings, SettingRequest request);

}
