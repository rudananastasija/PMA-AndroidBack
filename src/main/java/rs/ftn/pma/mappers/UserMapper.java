package rs.ftn.pma.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ftn.pma.dto.UserDto;
import rs.ftn.pma.dto.UserProfileResponse;
import rs.ftn.pma.dto.UserResponse;
import rs.ftn.pma.model.User;
import rs.ftn.pma.model.UserSettings;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "name", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    UserResponse mapToResponse(User user);

    @Mapping(source = "name", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    UserProfileResponse mapToProfileResponse(User user);

    @Mapping(source = "height", target = "height")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "sex", target = "gender")
    @Mapping(source = "waterReminder", target = "waterReminder")
    void  mapToProfileResponse2(UserSettings user, @MappingTarget UserProfileResponse profile);


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "firstname", target = "name")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phone", target = "number")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password", qualifiedByName = "passwordPrepare")
    User mapRequestToUser(UserDto userDto);

    @Named("passwordPrepare")
    static String preparePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "name", source = "firstname")
    @Mapping(target = "lastName", source="lastname")
    @Mapping(target = "email", source = "email")
    void patchMappingUser(@MappingTarget User user, UserProfileResponse profile);

}
