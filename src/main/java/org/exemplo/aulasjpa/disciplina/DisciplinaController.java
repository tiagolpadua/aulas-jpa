package org.exemplo.aulasjpa.disciplina;

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
@RequestMapping("/disciplinas")
@Transactional
public class DisciplinaController {

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @GetMapping
  public List<Disciplina> listar() {
    return disciplinaRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Disciplina> recuperarPorId(@PathVariable Long id) {
    return disciplinaRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Disciplina> cadastrar(@RequestBody Disciplina novoDisciplina, UriComponentsBuilder uriBuilder) {
    Disciplina disciplinaSalvo = disciplinaRepository.save(novoDisciplina);

    URI location = uriBuilder.path("/disciplinas/{id}").buildAndExpand(novoDisciplina.getId()).toUri();

    return ResponseEntity.created(location).body(disciplinaSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Disciplina> alterar(@PathVariable Long id, @RequestBody Disciplina disciplinaAlterado) {
    return disciplinaRepository.findById(id).map(disciplinaCadastrado -> {
      disciplinaCadastrado.setNome(disciplinaAlterado.getNome());
      return ResponseEntity.ok(disciplinaCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Disciplina> apagar(@PathVariable Long id) {
    Optional<Disciplina> possivelDisciplina = disciplinaRepository.findById(id);
    possivelDisciplina.ifPresent(disciplinaRepository::delete);

    return possivelDisciplina.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}