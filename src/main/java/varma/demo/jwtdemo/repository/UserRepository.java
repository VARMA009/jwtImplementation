package varma.demo.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import varma.demo.jwtdemo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByUsername(String username);

	User findUserByEmail(String email);
}
