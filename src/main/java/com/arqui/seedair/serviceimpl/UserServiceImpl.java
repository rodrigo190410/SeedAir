package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.UserDTO;
import com.arqui.seedair.entities.Authority;
import com.arqui.seedair.entities.User;
import com.arqui.seedair.repositories.UserRepository;
//import com.arqui.seedair.services.AuthorityService;
import com.arqui.seedair.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    AuthorityService authorityService;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    private List<Authority> authoritiesFromString(String authorities) {

        List<Authority> authorityList = new ArrayList<>();
        List<String> authorityStringList = Arrays.stream(authorities.split(";")).toList();
//        for (String authorityString : authorityStringList) {
//            Authority authority = authorityService.findByName(authorityString);
//            if (authority != null) {
//                authorityList.add(authority);
//            }
//        }
        return authorityList;
    }

    @Override
    public UserDTO addDTO(UserDTO userDTO) {
        List<Authority> authorityList = authoritiesFromString(userDTO.getAuthorities());

        User newUser = new User(null, userDTO.getUsername(),
                new BCryptPasswordEncoder().encode(userDTO.getPassword()), authorityList, null);

        newUser = userRepository.save(newUser);
        userDTO.setId(newUser.getId());
        return userDTO;
    }
}

