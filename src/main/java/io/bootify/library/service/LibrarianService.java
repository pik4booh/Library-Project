package io.bootify.library.service;

import io.bootify.library.domain.Librarian;
import io.bootify.library.domain.Role;
import io.bootify.library.model.LibrarianDTO;
import io.bootify.library.repos.LibrarianRepository;
import io.bootify.library.repos.RoleRepository;
import io.bootify.library.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;
    private final RoleRepository roleRepository;

    public LibrarianService(final LibrarianRepository librarianRepository,
            final RoleRepository roleRepository) {
        this.librarianRepository = librarianRepository;
        this.roleRepository = roleRepository;
    }

    public List<LibrarianDTO> findAll() {
        final List<Librarian> librarians = librarianRepository.findAll(Sort.by("idLibrarian"));
        return librarians.stream()
                .map(librarian -> mapToDTO(librarian, new LibrarianDTO()))
                .toList();
    }

    public LibrarianDTO get(final Integer idLibrarian) {
        return librarianRepository.findById(idLibrarian)
                .map(librarian -> mapToDTO(librarian, new LibrarianDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LibrarianDTO librarianDTO) {
        final Librarian librarian = new Librarian();
        mapToEntity(librarianDTO, librarian);
        return librarianRepository.save(librarian).getIdLibrarian();
    }

    public void update(final Integer idLibrarian, final LibrarianDTO librarianDTO) {
        final Librarian librarian = librarianRepository.findById(idLibrarian)
                .orElseThrow(NotFoundException::new);
        mapToEntity(librarianDTO, librarian);
        librarianRepository.save(librarian);
    }

    public void delete(final Integer idLibrarian) {
        librarianRepository.deleteById(idLibrarian);
    }

    private LibrarianDTO mapToDTO(final Librarian librarian, final LibrarianDTO librarianDTO) {
        librarianDTO.setIdLibrarian(librarian.getIdLibrarian());
        librarianDTO.setName(librarian.getName());
        librarianDTO.setPwd(librarian.getPwd());
        librarianDTO.setRole(librarian.getRole() == null ? null : librarian.getRole().getIdRole());
        return librarianDTO;
    }

    private Librarian mapToEntity(final LibrarianDTO librarianDTO, final Librarian librarian) {
        librarian.setName(librarianDTO.getName());
        librarian.setPwd(librarianDTO.getPwd());
        final Role role = librarianDTO.getRole() == null ? null : roleRepository.findById(librarianDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        librarian.setRole(role);
        return librarian;
    }

}
