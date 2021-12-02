package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.MenuSection;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.MenuSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuSectionService {
    private final MenuSectionRepository menuSectionRepository;
    private final TokenService tokenService;

    @Autowired
    public MenuSectionService(MenuSectionRepository menuSectionRepository, TokenService tokenService) {
        this.menuSectionRepository = menuSectionRepository;
        this.tokenService = tokenService;
    }

    public List<MenuSection> getMenuSections() {
        return menuSectionRepository.findAll();
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
        var sectionToUpdate = menuSectionRepository.findById(section.getId());
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
        var sectionToDelete = menuSectionRepository.findById(id);
        if (sectionToDelete.isPresent()) {
            menuSectionRepository.delete(sectionToDelete.get());
            return sectionToDelete.get();
        }
        return null;
    }
}
