package com.arqui.seedair.services;

import com.arqui.seedair.dtos.UserDTO;
import com.arqui.seedair.entities.User;

public interface UserService {
    public User add(User user);
    public User findById(Long id);
    public User findByUsername(String username);
    public UserDTO addDTO(UserDTO userDTO);
}
