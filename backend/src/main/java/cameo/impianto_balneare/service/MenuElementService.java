package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.MenuElement;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.repository.MenuElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (tokenCheck(tokenId)) {
            return null;
        }
        if (menuElementRepository.findAll().stream().anyMatch(menuElement ->
                element.getName().equalsIgnoreCase(menuElement.getName()))) {
            return null;
        }
        element.setElementVisible(true);
        return menuElementRepository.save(element);
    }


    public MenuElement updateElement(MenuElement element, String tokenId) {
        if (tokenCheck(tokenId)) {
            return null;
        }
        var elementToUpdate = menuElementRepository.findAll().stream().filter(e->e.getId().equals(element.getId())).findFirst();
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
        if (tokenCheck(tokenId)) {
            return null;
        }
        var elementToDelete = menuElementRepository.findAll().stream().filter(e->e.getId().equals(id)).findFirst();
        if (elementToDelete.isPresent()) {
            menuElementRepository.delete(elementToDelete.get());
            return elementToDelete.get();
        }
        return null;
    }

    public MenuElement toggleMenuElementVisibility(UUID id, String token) {
        if (tokenCheck(token)) {
            return null;
        }
        var elementToToggle = menuElementRepository.findAll().stream().filter(e->e.getId().equals(id)).findFirst();
        if (elementToToggle.isPresent()) {
            var elementToEdit = elementToToggle.get();
            elementToEdit.setElementVisible(!elementToEdit.isElementVisible());
            return menuElementRepository.save(elementToEdit);
        }
        return null;
    }

    public List<MenuElement> getMenuElements(String token) {
        if(tokenCheck(token)) {
            return menuElementRepository.findAll().stream()
                    .filter(MenuElement::isElementVisible).collect(Collectors.toList());
        }
        return menuElementRepository.findAll();
    }

    public List<MenuElement> getMenuElements() {
        return getMenuElements(null);
    }

    private boolean tokenCheck(String token){
        return !tokenService.checkToken(token, Role.BAR) && !tokenService.checkToken(token, Role.ADMIN);
    }
}
