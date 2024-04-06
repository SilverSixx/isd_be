package com.example.demo.modules.auth.services;

import com.example.demo.modules.admin.Admin;
import com.example.demo.modules.admin.AdminRepository;
import com.example.demo.modules.auth.services.impl.UserService;
import com.example.demo.modules.parents.Parent;
import com.example.demo.modules.parents.ParentRepository;
import com.example.demo.modules.teachers.Teacher;
import com.example.demo.modules.teachers.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                final Optional<Admin> admin = adminRepository.findByUsername(username);
                if (admin.isPresent()) {
                    return admin.get();
                }
                final Optional<Teacher> teacher = teacherRepository.findByUsername(username);
                if (teacher.isPresent()) {
                    return teacher.get();
                }
                final Optional<Parent> parent = parentRepository.findByUsername(username);
                if (parent.isPresent()) {
                    return parent.get();
                }
                throw new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username);
            }
        };
    }
}
