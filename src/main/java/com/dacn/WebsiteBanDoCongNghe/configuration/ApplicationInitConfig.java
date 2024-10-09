package com.dacn.WebsiteBanDoCongNghe.configuration;

import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.entity.Role;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.RoleReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

//    Tạo admin nếu chưa có tài khoản admin
    @Bean
    ApplicationRunner applicationRunner(UserReponsitory userReponsitory, RoleReponsitory roleReponsitory){
        return args -> {
            if(userReponsitory.findByUsername("admin").isEmpty()){
                roleReponsitory.save(Role.builder()
                                .name("USER")
                                .description("User role")
                        .build());

                Role adminRole = roleReponsitory.save(Role.builder()
                                .name("ADMIN")
                                .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userReponsitory.save(user);
            }
        };
    }
}
