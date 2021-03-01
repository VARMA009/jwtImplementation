package varma.demo.jwtdemo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import varma.demo.jwtdemo.domain.User;
import varma.demo.jwtdemo.exception.domain.EmailExistException;
import varma.demo.jwtdemo.exception.domain.EmailNotFoundException;
import varma.demo.jwtdemo.exception.domain.UserNotFoundException;
import varma.demo.jwtdemo.exception.domain.UsernameExistException;

public interface UserService {

	User register(String firstName, String lastName, String username, String email)
			throws UserNotFoundException, UsernameExistException, EmailExistException;

	List<User> getUsers();

	User findByUserName(String username);

	User findUserByEmail(String email);

	User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked,
			boolean isActive, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, IOException, EmailExistException;

	User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

	void deleteUser(String username) throws IOException;

	void resetPassword(String email) throws EmailNotFoundException;

}
