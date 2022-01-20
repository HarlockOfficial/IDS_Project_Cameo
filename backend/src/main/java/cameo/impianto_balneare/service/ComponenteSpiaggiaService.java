package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.ComponenteSpiaggia;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.repository.ComponenteSpiaggiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponenteSpiaggiaService {
    private final TokenService tokenService;
    private final ComponenteSpiaggiaRepository componenteSpiaggiaRepository;

    @Autowired
    public ComponenteSpiaggiaService(TokenService tokenService, ComponenteSpiaggiaRepository componenteSpiaggiaRepository) {
        this.tokenService = tokenService;
        this.componenteSpiaggiaRepository = componenteSpiaggiaRepository;
    }

    public ComponenteSpiaggia getComponenteSpiaggia(String name) {
        return getComponenteSpiaggiaList().stream()
                .filter(e -> e.getNome().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<ComponenteSpiaggia> getComponenteSpiaggiaList() {
        return componenteSpiaggiaRepository.findAll();
    }

    public ComponenteSpiaggia addComponenteSpiaggia(ComponenteSpiaggia componenteSpiaggia, String token) {
        if(!tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        var list = getComponenteSpiaggiaList();
        componenteSpiaggia.setNome(componenteSpiaggia.getNome().toLowerCase());
        var componente = list.stream().filter(e -> e.getNome().equalsIgnoreCase(componenteSpiaggia.getNome())).findFirst();
        if(componente.isPresent()){
            var comp = componente.get();
            comp.setQuantity(comp.getQuantity() + componenteSpiaggia.getQuantity());
            return componenteSpiaggiaRepository.save(comp);
        }
        return componenteSpiaggiaRepository.save(componenteSpiaggia);
    }

    public ComponenteSpiaggia updateComponenteSpiaggia(ComponenteSpiaggia componenteSpiaggia, String token) {
        if(!tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        var list = getComponenteSpiaggiaList();
        var componente = list.stream().filter(e -> e.getNome().equalsIgnoreCase(componenteSpiaggia.getNome())).findFirst();
        if(componente.isPresent()){
            var comp = componente.get();
            comp.setQuantity(componenteSpiaggia.getQuantity());
            return componenteSpiaggiaRepository.save(comp);
        }
        return null;
    }

    public ComponenteSpiaggia deleteComponenteSpiaggia(String name, String token) {
        if(!tokenService.checkToken(token, Role.ADMIN)) {
            return null;
        }
        var list = getComponenteSpiaggiaList();
        var componente = list.stream().filter(e -> e.getNome().equalsIgnoreCase(name)).findFirst();
        if(componente.isPresent()){
            var comp = componente.get();
            componenteSpiaggiaRepository.delete(comp);
            return comp;
        }
        return null;
    }
}
