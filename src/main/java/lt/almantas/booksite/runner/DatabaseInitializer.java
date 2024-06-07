package lt.almantas.booksite.runner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.almantas.booksite.model.Entity.Roles;
import lt.almantas.booksite.model.Entity.User;
import lt.almantas.booksite.repositories.UserRepository;
import lt.almantas.booksite.security.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty() && userRepository.findByUsername("user").isEmpty()) {
            User adminUser = new User();
            User normalUser = new User();

            adminUser.setRoleID(Roles.ADMIN.getRoleId());
            normalUser.setRoleID(Roles.USER.getRoleId());

            adminUser.setUsername("admin");
            adminUser.setEmail("admin@gmail.com");

            normalUser.setUsername("user");
            normalUser.setEmail("user@gmail.com");

            BCrypt bcrypt = new BCrypt();
            adminUser.setPassword(bcrypt.hashPassword("admin"));
            normalUser.setPassword(bcrypt.hashPassword("user"));

            userRepository.save(adminUser);
            userRepository.save(normalUser);
        }
    }
}