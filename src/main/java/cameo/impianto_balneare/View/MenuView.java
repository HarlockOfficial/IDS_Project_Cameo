package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.MenuElement;
import cameo.impianto_balneare.Entity.MenuSection;
import cameo.impianto_balneare.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class MenuView {
    private final MenuService menuService;

    @Autowired
    public MenuView(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ResponseEntity<Map<MenuSection, List<MenuElement>>> getMenu() {
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
}
