package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.Role;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleReponsitory extends JpaRepository<Role,String> {
    Optional<Role> findByName(String name);
}
