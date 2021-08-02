package org.exemplo.aulasjpa.turma;

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
@RequestMapping("/turmas")
@Transactional
public class TurmaController {

  @Autowired
  private TurmaRepository turmaRepository;

  @GetMapping
  public List<Turma> listar() {
    return turmaRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Turma> recuperarPorId(@PathVariable Long id) {
    return turmaRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Turma> cadastrar(@RequestBody Turma novoTurma, UriComponentsBuilder uriBuilder) {
    Turma turmaSalvo = turmaRepository.save(novoTurma);

    URI location = uriBuilder.path("/turmas/{id}").buildAndExpand(novoTurma.getId()).toUri();

    return ResponseEntity.created(location).body(turmaSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Turma> alterar(@PathVariable Long id, @RequestBody Turma turmaAlterada) {
    return turmaRepository.findById(id).map(turmaCadastrado -> {
      turmaCadastrado.setDescricao(turmaAlterada.getDescricao());
      return ResponseEntity.ok(turmaCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Turma> apagar(@PathVariable Long id) {
    Optional<Turma> possivelTurma = turmaRepository.findById(id);
    possivelTurma.ifPresent(turmaRepository::delete);

    return possivelTurma.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}