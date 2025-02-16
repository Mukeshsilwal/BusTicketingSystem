//package com.Transaction.transaction.config;
//
//import com.Transaction.transaction.entity.Role1;
//import com.Transaction.transaction.entity.Users;
//import com.Transaction.transaction.repository.UserRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class SuperUserInitializer implements ApplicationRunner {
//
//    private final UserRepo userRepository;
//    private final PasswordEncoder encoder;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        if (!userRepository.existsByEmail("mukeshsilwal5@gmail.com")) {
//            Users superUser = new Users();
//            superUser.setEmail("mukeshsilwal5@gmail.com");
//            superUser.setPassword(encoder.encode("123456"));
//            superUser.setRole1(Role1.SUPER_ADMIN);
//            superUser.setRole(Role1.SUPER_ADMIN);
//            userRepository.save(superUser);
//        }
//    }
//}
//
