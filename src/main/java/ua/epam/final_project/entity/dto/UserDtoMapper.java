package ua.epam.final_project.entity.dto;

import ua.epam.final_project.entity.User;

/**
 * UserService Utility class for turning DTOs into objects and vice versa
 */
public class UserDtoMapper {

    private UserDtoMapper() {
    }

    /**
     * Convert Entity into Dto
     * @param user - user entity
     * @return userDto
     */
    public static UserDto convertEntityIntoDto(User user) {
        if (user != null) {
            return new UserDto(user);
        }
        return null;
    }

    /**
     * Convert Dto into Entity
     * @param userDto - userDto entity
     * @return user entity
     */
    public static User convertDtoIntoEntity(UserDto userDto){
        if (userDto != null) {
            User user = new User();
            user.setId(userDto.getId());
            user.setLogin(user.getLogin());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setName(userDto.getName());
            user.setUserImage(userDto.getUserImage());
            user.setBalance(userDto.getBalance());
            user.setRole(userDto.getRole());

            return user;
        }
        return null;
    }
}
