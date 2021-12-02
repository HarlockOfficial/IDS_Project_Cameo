package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.MenuElement;
import cameo.impianto_balneare.Entity.MenuSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuSectionService menuSectionService;
    private final MenuElementService menuElementService;

    @Autowired
    public MenuService(MenuSectionService menuSectionService, MenuElementService menuElementService) {
        this.menuSectionService = menuSectionService;
        this.menuElementService = menuElementService;
    }

    public Map<MenuSection, List<MenuElement>> getMenu() {
        var sections = menuSectionService.getMenuSections();
        var outputMap = new HashMap<MenuSection, List<MenuElement>>();
        sections.forEach(section -> outputMap.put(section, section.getMenuElements()));
        return outputMap;
    }

    public MenuSection addSection(MenuSection section, String tokenId) {
        return menuSectionService.addSection(section, tokenId);
    }

    public MenuSection updateSection(MenuSection section, String tokenId) {
        return menuSectionService.updateSection(section, tokenId);
    }

    public MenuSection deleteSection(UUID id, String token) {
        return menuSectionService.deleteSection(id, token);
    }

    public MenuElement addElement(MenuElement element, String tokenId) {
        return menuElementService.addElement(element, tokenId);
    }

    public MenuElement updateElement(MenuElement element, String tokenId) {
        return menuElementService.updateElement(element, tokenId);
    }

    public MenuElement deleteElement(UUID id, String tokenId) {
        return menuElementService.deleteElement(id, tokenId);
    }
}
