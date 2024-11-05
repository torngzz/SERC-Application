package com.aub.backend_aub_shop.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.dto.UserDTO;
import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.repository.UserRepository;
import com.aub.backend_aub_shop.util.UserSessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;  // Assuming this service has a method to get a username by ID

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

@Autowired
    private EmailService emailService;

    public String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int password = 100000 + random.nextInt(900000);
        return String.valueOf(password);
    }

    // public String encryptPassword(String plainPassword) {
    //     return passwordEncoder.encode(plainPassword);
    // }

    // @Transactional
    // public UserModel create(HttpServletRequest request, UserModel user) {
    //     checkForDuplicateUser(user);

    //     HttpSession session = request.getSession();
    //     Long userId = UserSessionUtils.getUserId(session);

    //     if (userId == null) {
    //         LOGGER.info("User ID not found in session. Cannot create user.");
    //     }

    //     String rawPassword = generateRandomPassword();
    //     String encryptedPassword = passwordEncoder.encode(rawPassword);// Encrypt the generated password
    //     emailService.sendEmail(user.getEmail(), "AUB E-Shop Password","Your password to login to AUB E-Shop is : " + rawPassword);
        
    //     user.setCreatedDate(new Date());
    //     user.setUpdatedDate(new Date());
    //     user.setPassword(encryptedPassword); 
    //     user.setCreatedBy(userId);
    //     user.setUpdatedBy(userId);
        
    //     return userRepo.save(user);
    // }
        private static final Pattern LETTERS_ONLY_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    // Matches only numbers (0-9)
        private static final Pattern NUMBER_ONLY_PATTERN = Pattern.compile("^[0-9]+$");

        // Matches valid email addresses
        private static final Pattern EMAIL_ONLY_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");    // private static final Pattern LETTERS_ONLY_PATTERN = Pattern.compile("^[a-zA-Z]+$");

        @Transactional
        public UserModel create(HttpServletRequest request, UserModel user) {
            // Validate the username
            validateUsername(user.getUsername());
            validateEmail(user.getEmail());
            validatePhone(user.getPhone());

            checkForDuplicateUser(user);

            HttpSession session = request.getSession();
            Long userId = UserSessionUtils.getUserId(session);

            if (userId == null) {
                LOGGER.info("User ID not found in session. Cannot create user.");
            }

            String rawPassword = generateRandomPassword();
            String encryptedPassword = passwordEncoder.encode(rawPassword);
            emailService.sendEmail(user.getEmail(), "AUB E-Shop Password", "Your password to login to AUB E-Shop is : " + rawPassword);

            user.setStatus(1);
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());
            user.setPassword(encryptedPassword); 
            user.setCreatedBy(userId);
            user.setUpdatedBy(userId);

            return userRepo.save(user);
        }

        public void validateUsername(String username) {
            if (username == null || !LETTERS_ONLY_PATTERN.matcher(username).matches()) {
                throw new IllegalArgumentException("Username must only contain letters.");
            }
        }

        public void validateEmail(String email) {
            if (email == null || !EMAIL_ONLY_PATTERN.matcher(email).matches()) {
                throw new IllegalArgumentException("Username must only contain letters.");
            }
        }

        public void validatePhone(String phone) {
            if (phone == null || !NUMBER_ONLY_PATTERN.matcher(phone).matches()) {
                throw new IllegalArgumentException("Username must only contain letters.");
            }
        }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading user by username: {}", username);
        UserModel user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public UserModel findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Page<UserDTO> findAll(String username, int pageNumber, int pageSize) {
        Page<UserModel> users = userRepository.findByUsernameContaining(username, PageRequest.of(pageNumber, pageSize));

        return users.map(user -> {
            
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setRole(user.getRole());
            
            if (user.getCreatedDate() != null) {
                dto.setCreatedDate(user.getCreatedDate());
            }

            if (user.getUpdatedDate() != null) {
                dto.setUpdatedDate(user.getUpdatedDate());
            }
            dto.setStatus(getStatus(user.getStatus()));
            dto.setCreatedByUsername(userService.getUsernameById(user.getCreatedBy()));
            dto.setUpdatedByUsername(userService.getUsernameById(user.getUpdatedBy()));
            return dto;
        });
    }

    public String getUsernameById(Long userId) {
        LOGGER.info("User ID is : "  + userId);
        if(userId == 0L){
            return "System";
        }
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    private String getStatus(int statusId){
        if(statusId == 0){
            return "Disabled";
        }
        return "Enabled";
    }

    public List<UserModel> findAll() {
        return userRepo.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return userRepo.findById(id);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public UserModel update(UserModel user, Long id, HttpServletRequest request) {
        // Find the existing user by ID
        UserModel userModel = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check for duplicate usernames
        checkForDuplicateUser(user, id);

        // Get the current user ID from the session
        HttpSession session = request.getSession();
        Long getIdSession = UserSessionUtils.getUserId(session);

        // Update user details
        userModel.setUsername(user.getUsername());
        userModel.setRole(user.getRole());
        userModel.setPhone(user.getPhone());
        userModel.setEmail(user.getEmail()); 
        userModel.setUpdatedDate(new Date());
        userModel.setUpdatedBy(getIdSession); // Set the ID instead of converting to String

        LOGGER.info("Updating user with ID: {}. Preserving createdDate: {}", id, userModel.getCreatedDate());

        // Save and return the updated user
        return userRepo.save(userModel);
    }

    //Used in Create Process
    private void checkForDuplicateUser(UserModel user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("This username already exists!");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("This email already exists!");
        }
        if (userRepo.existsByPhone(user.getPhone())) {
            throw new IllegalArgumentException("This phone number already exists!");
        }
    }

    //Used for Update Process
    private void checkForDuplicateUser(UserModel user, Long id) {
        Optional<UserModel> existingUser = userRepo.findByUsernameOrEmailOrPhoneAndIdNot(user.getUsername(), user.getEmail(), user.getPhone(), id);
        if (existingUser.isPresent()) {
            UserModel existing = existingUser.get();
            if (!existing.getId().equals(id)) {
                if (!existing.getUsername().equals(user.getUsername())) {
                    throw new IllegalArgumentException("This username already exists!");
                }
                if (!existing.getEmail().equals(user.getEmail())) {
                    throw new IllegalArgumentException("This email already exists!");
                }
                if (!existing.getPhone().equals(user.getPhone())) {
                    throw new IllegalArgumentException("This phone number already exists!");
                }
            }
        }
    }
}