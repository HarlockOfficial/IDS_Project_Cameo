package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.MenuElement;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.MenuElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuElementService {
    private final MenuElementRepository menuElementRepository;
    private final TokenService tokenService;

    @Autowired
    public MenuElementService(MenuElementRepository menuElementRepository, TokenService tokenService) {
        this.menuElementRepository = menuElementRepository;
        this.tokenService = tokenService;
    }

    public MenuElement addElement(MenuElement element, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.BAR) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        if (menuElementRepository.findAll().stream().anyMatch(menuElement ->
                element.getName().equalsIgnoreCase(menuElement.getName()))) {
            return null;
        }
        return menuElementRepository.save(element);
    }


    public MenuElement updateElement(MenuElement element, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.BAR) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var elementToUpdate = menuElementRepository.findById(element.getId());
        if (elementToUpdate.isPresent()) {
            var elementToEdit = elementToUpdate.get();
            elementToEdit.setName(element.getName());
            elementToEdit.setDescription(element.getDescription());
            elementToEdit.setPrice(element.getPrice());
            return menuElementRepository.save(elementToEdit);
        }
        return null;
    }

    public MenuElement deleteElement(UUID id, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.BAR) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var elementToDelete = menuElementRepository.findById(id);
        if (elementToDelete.isPresent()) {
            menuElementRepository.delete(elementToDelete.get());
            return elementToDelete.get();
        }
        return null;
    }

    public MenuElement toggleMenuElementVisibility(UUID id, String token) {
        if (!tokenService.checkToken(token, Role.BAR) && !tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        var elementToToggle = menuElementRepository.findById(id);
        if (elementToToggle.isPresent()) {
            var elementToEdit = elementToToggle.get();
            elementToEdit.toggleElementVisibility();
            return menuElementRepository.save(elementToEdit);
        }
        return null;
    }

    public List<MenuElement> getMenuElements(String token) {
        if(!tokenService.checkToken(token, Role.BAR) && !tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        return menuElementRepository.findAll();
    }
}
