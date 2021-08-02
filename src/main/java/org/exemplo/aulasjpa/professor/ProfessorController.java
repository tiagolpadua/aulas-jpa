package org.exemplo.aulasjpa.professor;

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
@RequestMapping("/professores")
@Transactional
public class ProfessorController {

  @Autowired
  private ProfessorRepository professorRepository;

  @GetMapping
  public List<Professor> listar() {
    return professorRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Professor> recuperarPorId(@PathVariable Long id) {
    return professorRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Professor> cadastrar(@RequestBody Professor novoProfessor, UriComponentsBuilder uriBuilder) {
    Professor professorSalvo = professorRepository.save(novoProfessor);

    URI location = uriBuilder.path("/professors/{id}").buildAndExpand(novoProfessor.getId()).toUri();

    return ResponseEntity.created(location).body(professorSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Professor> alterar(@PathVariable Long id, @RequestBody Professor professorAlterado) {
    return professorRepository.findById(id).map(professorCadastrado -> {
      professorCadastrado.setNome(professorAlterado.getNome());
      return ResponseEntity.ok(professorCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Professor> apagar(@PathVariable Long id) {
    Optional<Professor> possivelProfessor = professorRepository.findById(id);
    possivelProfessor.ifPresent(professorRepository::delete);

    return possivelProfessor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}