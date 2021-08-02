package org.exemplo.aulasjpa.materia;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/materias")
@Transactional
public class MateriaController {

  @Autowired
  private MateriaRepository materiaRepository;

  @GetMapping
  public List<Materia> listar() {
    return materiaRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Materia> recuperarPorId(@PathVariable Long id) {
    return materiaRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Materia> cadastrar(@RequestBody Materia novoMateria, UriComponentsBuilder uriBuilder) {
    Materia materiaSalvo = materiaRepository.save(novoMateria);

    URI location = uriBuilder.path("/materias/{id}").buildAndExpand(novoMateria.getId()).toUri();

    return ResponseEntity.created(location).body(materiaSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Materia> alterar(@PathVariable Long id, @RequestBody Materia materiaAlterado) {
    return materiaRepository.findById(id).map(materiaCadastrado -> {
      materiaCadastrado.setNome(materiaAlterado.getNome());
      return ResponseEntity.ok(materiaCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Materia> apagar(@PathVariable Long id) {
    Optional<Materia> possivelMateria = materiaRepository.findById(id);
    possivelMateria.ifPresent(materiaRepository::delete);

    return possivelMateria.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}