package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.MenuElement;
import cameo.impianto_balneare.entity.MenuSection;
import cameo.impianto_balneare.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class MenuView implements GlobalExceptionHandler{
    private final MenuService menuService;

    @Autowired
    public MenuView(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ResponseEntity<Map<MenuSection, List<MenuElement>>> getVisibleMenu() {
        var menu = menuService.getMenu();
        return ResponseEntity.ok(menu);
    }

    @RequestMapping(value = "/menu/section", method = RequestMethod.POST)
    public ResponseEntity<MenuSection> addSection(@RequestBody MenuSection name, @RequestHeader("token") String token) {
        var section = menuService.addSection(name, token);
        if (section == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(section);
    }

    @RequestMapping(value = "/menu/section", method = RequestMethod.PUT)
    public ResponseEntity<MenuSection> updateSection(@RequestBody MenuSection name, @RequestHeader("token") String token) {
        var section = menuService.updateSection(name, token);
        if (section == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(section);
    }

    @RequestMapping(value = "/menu/section/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MenuSection> deleteSection(@PathVariable UUID id, @RequestHeader("token") String token) {
        var section = menuService.deleteSection(id, token);
        if (section == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(section);
    }

    @RequestMapping(value = "/menu/element", method = RequestMethod.POST)
    public ResponseEntity<MenuElement> addElement(@RequestBody MenuElement name, @RequestHeader("token") String token) {
        System.out.println(name.getName() + " " + name.getDescription() + " "+ name.getPrice() + name.getSection());
        var element = menuService.addElement(name, token);
        if (element == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(element);
    }

    @RequestMapping(value = "/menu/element", method = RequestMethod.PUT)
    public ResponseEntity<MenuElement> updateElement(@RequestBody MenuElement name, @RequestHeader("token") String token) {
        var element = menuService.updateElement(name, token);
        if (element == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(element);
    }

    @RequestMapping(value = "/menu/element/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MenuElement> deleteElement(@PathVariable UUID id, @RequestHeader("token") String token) {
        var element = menuService.deleteElement(id, token);
        if (element == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(element);
    }

    @RequestMapping(value = "/menu/element/visibility/{id}", method = RequestMethod.POST)
    public ResponseEntity<MenuElement> toggleMenuElementVisibility(@PathVariable UUID id, @RequestHeader("token") String token) {
        var element = menuService.toggleMenuElementVisibility(id, token);
        if (element == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(element);
    }

    @RequestMapping(value = "/menu/section/visibility/{id}", method = RequestMethod.POST)
    public ResponseEntity<MenuSection> toggleMenuSectionVisibility(@PathVariable UUID id, @RequestHeader("token") String token) {
        var element = menuService.toggleMenuSectionVisibility(id, token);
        if (element == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(element);
    }

    @RequestMapping(value = "/menu/section", method = RequestMethod.GET)
    public ResponseEntity<List<MenuSection>> getAllSections(@RequestHeader("token") String token) {
        var sections = menuService.getAllSections(token);
        if (sections == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sections);
    }

    @RequestMapping(value = "/menu/element", method = RequestMethod.GET)
    public ResponseEntity<List<MenuElement>> getAllElements(@RequestHeader("token") String token) {
        var elements = menuService.getAllElements(token);
        if (elements == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(elements);
    }
}
