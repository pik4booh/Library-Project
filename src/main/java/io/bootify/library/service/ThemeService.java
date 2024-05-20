package io.bootify.library.service;

import io.bootify.library.domain.BookTheme;
import io.bootify.library.domain.Theme;
import io.bootify.library.model.ThemeDTO;
import io.bootify.library.repos.BookThemeRepository;
import io.bootify.library.repos.ThemeRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final BookThemeRepository bookThemeRepository;

    public ThemeService(final ThemeRepository themeRepository,
            final BookThemeRepository bookThemeRepository) {
        this.themeRepository = themeRepository;
        this.bookThemeRepository = bookThemeRepository;
    }

    public List<ThemeDTO> findAll() {
        final List<Theme> themes = themeRepository.findAll(Sort.by("idTheme"));
        return themes.stream()
                .map(theme -> mapToDTO(theme, new ThemeDTO()))
                .toList();
    }

    public ThemeDTO get(final Integer idTheme) {
        return themeRepository.findById(idTheme)
                .map(theme -> mapToDTO(theme, new ThemeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ThemeDTO themeDTO) {
        final Theme theme = new Theme();
        mapToEntity(themeDTO, theme);
        return themeRepository.save(theme).getIdTheme();
    }

    public void update(final Integer idTheme, final ThemeDTO themeDTO) {
        final Theme theme = themeRepository.findById(idTheme)
                .orElseThrow(NotFoundException::new);
        mapToEntity(themeDTO, theme);
        themeRepository.save(theme);
    }

    public void delete(final Integer idTheme) {
        themeRepository.deleteById(idTheme);
    }

    private ThemeDTO mapToDTO(final Theme theme, final ThemeDTO themeDTO) {
        themeDTO.setIdTheme(theme.getIdTheme());
        themeDTO.setName(theme.getName());
        return themeDTO;
    }

    private Theme mapToEntity(final ThemeDTO themeDTO, final Theme theme) {
        theme.setName(themeDTO.getName());
        return theme;
    }

    public ReferencedWarning getReferencedWarning(final Integer idTheme) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Theme theme = themeRepository.findById(idTheme)
                .orElseThrow(NotFoundException::new);
        final BookTheme themeBookTheme = bookThemeRepository.findFirstByTheme(theme);
        if (themeBookTheme != null) {
            referencedWarning.setKey("theme.bookTheme.theme.referenced");
            referencedWarning.addParam(themeBookTheme.getIdBookTheme());
            return referencedWarning;
        }
        return null;
    }

}
