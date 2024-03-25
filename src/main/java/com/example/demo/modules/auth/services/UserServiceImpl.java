package com.example.demo.modules.auth.services;

import com.example.demo.modules.admin.entities.Admin;
import com.example.demo.modules.admin.repositories.AdminRepository;
import com.example.demo.modules.teachers.entities.Teacher;
import com.example.demo.modules.teachers.repositories.TeacherRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Admin> adminOptional = adminRepository.findByUsername(username);
                if (adminOptional.isPresent()) {
                    return adminOptional.get();
                } else {
                    Optional<Teacher> teacherOptional = teacherRepository.findByUsername(username);
                    return teacherOptional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
                }
            }
        };
    }
}
