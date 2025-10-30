package ma.rest.spring.controllers;

import lombok.RequiredArgsConstructor;
import ma.rest.spring.entities.Compte;
import ma.rest.spring.repositories.CompteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/banque")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CompteController {

    private final CompteRepository repo;

    // Healthcheck simple
    @GetMapping("/ping")
    public String ping() { return "pong"; }

    // READ: tous les comptes (JSON & XML)
    @GetMapping(value = "/comptes", produces = {"application/json", "application/xml"})
    public List<Compte> all() {
        return repo.findAll();
    }

    // READ: compte par id (JSON & XML)
    @GetMapping(value = "/comptes/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> byId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: ajouter un compte (JSON & XML)
    @PostMapping(value = "/comptes",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> create(@RequestBody Compte c) {
        c.setId(null);
        Compte saved = repo.save(c);
        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved); // 201 + Location
    }

    // UPDATE: modifier un compte (JSON & XML)
    @PutMapping(value = "/comptes/{id}",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> update(@PathVariable Long id, @RequestBody Compte data) {
        return repo.findById(id).map(c -> {
            c.setSolde(data.getSolde());
            c.setDateCreation(data.getDateCreation());
            c.setType(data.getType());
            return ResponseEntity.ok(repo.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: supprimer un compte
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
