package com.yavirac.homies.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yavirac.homies.Rol.Role;
import com.yavirac.homies.Rol.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User usuario) {
        // Validación de campos del usuario
        if (usuario.getNombres() == null || usuario.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException("El campo nombres no puede estar vacío");
        }        
        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (usuario.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
        }
        if (usuario.getFechaRegistro() == null) {
            throw new IllegalArgumentException("La fecha de registro no puede estar vacía");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Roles asocioados
        Set<Role> roles = new HashSet<>();
        for (Role rol : usuario.getRoles()) {
            Role foundRol = roleRepository.findById(rol.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rol.getIdRole()));
            roles.add(foundRol);
        }
        usuario.setRoles(roles);
        // Guardar el usuario
        return userRepository.save(usuario);
    }

    public User getUserById(Long id_user) {
        return userRepository.findById(id_user).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                userRepository.delete(user);
    }

    public User updateUser(Long id_user, User userDetails) {
        User user = userRepository.findById(id_user)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setNombres(userDetails.getNombres());
        user.setTelefono(userDetails.getTelefono());
        user.setFechaNacimiento(userDetails.getFechaNacimiento());
        user.setFechaRegistro(userDetails.getFechaRegistro());

        // Roles actualizados
        Set<Role> roles = new HashSet<>();
        for (Role rol : userDetails.getRoles()) {
            Role foundRol = roleRepository.findById(rol.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rol.getIdRole()));
            roles.add(foundRol);
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

}
