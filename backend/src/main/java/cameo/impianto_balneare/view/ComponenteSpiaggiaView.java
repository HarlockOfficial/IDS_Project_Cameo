package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.ComponenteSpiaggia;
import cameo.impianto_balneare.service.ComponenteSpiaggiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComponenteSpiaggiaView implements GlobalExceptionHandler {
    private final ComponenteSpiaggiaService componenteSpiaggiaService;

    @Autowired
    public ComponenteSpiaggiaView(ComponenteSpiaggiaService componenteSpiaggiaService) {
        this.componenteSpiaggiaService = componenteSpiaggiaService;
    }

    @RequestMapping(value = "/componente", method = RequestMethod.GET)
    public ResponseEntity<ComponenteSpiaggia> getComponenteSpiaggia(@RequestBody String name){
        var data = componenteSpiaggiaService.getComponenteSpiaggia(name);
        if(data == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/componente/all", method = RequestMethod.GET)
    public ResponseEntity<List<ComponenteSpiaggia>> getComponenteSpiaggiaList(){
        var data = componenteSpiaggiaService.getComponenteSpiaggiaList();
        if(data == null || data.size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/componente", method = RequestMethod.POST)
    public ResponseEntity<ComponenteSpiaggia> addComponenteSpiaggia(@RequestBody ComponenteSpiaggia componenteSpiaggia, @RequestHeader String token){
        var data = componenteSpiaggiaService.addComponenteSpiaggia(componenteSpiaggia, token);
        if(data == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/componente", method = RequestMethod.PUT)
    public ResponseEntity<ComponenteSpiaggia> updateComponenteSpiaggia(@RequestBody ComponenteSpiaggia componenteSpiaggia, @RequestHeader String token){
        var data = componenteSpiaggiaService.updateComponenteSpiaggia(componenteSpiaggia, token);
        if(data == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/componente", method = RequestMethod.DELETE)
    public ResponseEntity<ComponenteSpiaggia> deleteComponenteSpiaggia(@RequestBody String name, @RequestHeader String token){
        var data = componenteSpiaggiaService.deleteComponenteSpiaggia(name, token);
        if(data == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(data);
    }
}
