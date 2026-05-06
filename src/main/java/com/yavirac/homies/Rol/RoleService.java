package com.yavirac.homies.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
    
    public Role createRol(Role rol) {
        return roleRepository.save(rol);
    }

    public Role getRolById(Long idRole) {
        return roleRepository.findById(idRole).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public void deleteRol(Long idRole) {
        Role rol = roleRepository.findById(idRole).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        roleRepository.delete(rol);
    }

    public Role updateRol(Long idRole, Role rolDetails) {
        Role rol = roleRepository.findById(idRole).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    
        // Actualiza los campos de 'rol' con los valores de 'rolDetails'
        rol.setName(rolDetails.getName());
    
        // Guarda y devuelve la entidad 'rol' actualizada
        return roleRepository.save(rol);
    }
}