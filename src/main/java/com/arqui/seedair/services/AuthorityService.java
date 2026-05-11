package com.arqui.seedair.services;

import com.arqui.seedair.entities.Authority;

public interface AuthorityService {

    public Authority findById(Long id);
    public Authority findByName(String name);
    public Authority add(Authority authority);

}
