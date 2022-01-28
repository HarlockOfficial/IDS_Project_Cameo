package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.MenuSection;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.repository.MenuSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuSectionService {
    private final MenuSectionRepository menuSectionRepository;
    private final TokenService tokenService;

    @Autowired
    public MenuSectionService(MenuSectionRepository menuSectionRepository, TokenService tokenService) {
        this.menuSectionRepository = menuSectionRepository;
        this.tokenService = tokenService;
    }

    public List<MenuSection> getMenuSections(String token) {
        if (tokenService.checkToken(token, Role.BAR) || tokenService.checkToken(token, Role.ADMIN)) {
            return menuSectionRepository.findAll();
        }
        return menuSectionRepository.findAll().stream().filter(MenuSection::isSectionVisible).collect(Collectors.toList());
    }
    public List<MenuSection> getMenuSections() {
        return getMenuSections(null);
    }
    public MenuSection addSection(MenuSection section, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.BAR) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        if (menuSectionRepository.findAll().stream().anyMatch(s ->
                section.getSectionName().equalsIgnoreCase(s.getSectionName()))) {
            return null;
        }
        return menuSectionRepository.save(section);
    }

    public MenuSection updateSection(MenuSection section, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.BAR) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var sectionToUpdate = menuSectionRepository.findAll().stream().filter(e->e.equals(section)).findFirst();
        if (sectionToUpdate.isPresent()) {
            var sectionToEdit = sectionToUpdate.get();
            sectionToEdit.setSectionName(section.getSectionName());
            return menuSectionRepository.save(sectionToEdit);
        }
        return null;
    }

    public MenuSection deleteSection(UUID id, String token) {
        if (!tokenService.checkToken(token, Role.BAR) && !tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        var sectionToDelete = menuSectionRepository.findAll().stream().filter(e->e.getId().equals(id)).findFirst();
        if (sectionToDelete.isPresent()) {
            menuSectionRepository.delete(sectionToDelete.get());
            return sectionToDelete.get();
        }
        return null;
    }

    public MenuSection toggleMenuSectionVisibility(UUID id, String token) {
        if (!tokenService.checkToken(token, Role.BAR) && !tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        var sectionToToggle = menuSectionRepository.findAll().stream().filter(e->e.getId().equals(id)).findFirst();
        if (sectionToToggle.isPresent()) {
            var sectionToEdit = sectionToToggle.get();
            sectionToEdit.setSectionVisible(!sectionToEdit.isSectionVisible());
            return menuSectionRepository.save(sectionToEdit);
        }
        return null;
    }
}
