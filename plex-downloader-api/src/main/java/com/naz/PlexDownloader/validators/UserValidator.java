package com.naz.PlexDownloader.validators;

import com.naz.PlexDownloader.dtos.UserDTO;
import com.naz.PlexDownloader.exceptions.RestException;
import com.naz.PlexDownloader.models.User;
import com.naz.PlexDownloader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            throw new RestException("Size.userForm.username", new Object[] {user.getUsername()});
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
            throw new RestException("Duplicate.userForm.username", new Object[] {user.getUsername()});
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            throw new RestException("Size.userForm.password", new Object[] {user.getPassword()});

        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}