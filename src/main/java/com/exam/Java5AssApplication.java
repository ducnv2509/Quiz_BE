package com.exam;

import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class Java5AssApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Java5AssApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

//        System.out.println("starting code");
//        User user = new User();
//        user.setFirstName("Nguyen");
//        user.setLastName("Duc");
//        user.setUsername("ducnv2509");
//        user.setPassword(this.bCryptPasswordEncoder.encode("123123"));
//        user.setEmail("ducnvph14435@gmail.com");
//        user.setPhone("0332429178");
//        user.setProfile("default.png");
//
//        Role role1 = new Role();
//        role1.setRoleId(44L);
//        role1.setRoleName("ADMIN");
//
//        Set<UserRole> userRoleSet = new HashSet<>();
//        UserRole userRole = new UserRole();
//        userRole.setRole(role1);
//        userRole.setUser(user);
//        userRoleSet.add(userRole);
//
//        User user1 = userService.createUser(user, userRoleSet);
//        System.out.println(user1.getUsername());
//
    }
}
