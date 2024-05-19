package io.bootify.library.service;

import io.bootify.library.domain.Librarian;
import io.bootify.library.domain.Role;
import io.bootify.library.model.RoleDTO;
import io.bootify.library.repos.LibrarianRepository;
import io.bootify.library.repos.RoleRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final LibrarianRepository librarianRepository;

    public RoleService(final RoleRepository roleRepository,
            final LibrarianRepository librarianRepository) {
        this.roleRepository = roleRepository;
        this.librarianRepository = librarianRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("idRole"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Integer idRole) {
        return roleRepository.findById(idRole)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getIdRole();
    }

    public void update(final Integer idRole, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(idRole)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Integer idRole) {
        roleRepository.deleteById(idRole);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setIdRole(role.getIdRole());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        return role;
    }

    public ReferencedWarning getReferencedWarning(final Integer idRole) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Role role = roleRepository.findById(idRole)
                .orElseThrow(NotFoundException::new);
        final Librarian roleLibrarian = librarianRepository.findFirstByRole(role);
        if (roleLibrarian != null) {
            referencedWarning.setKey("role.librarian.role.referenced");
            referencedWarning.addParam(roleLibrarian.getIdLibrarian());
            return referencedWarning;
        }
        return null;
    }

}
