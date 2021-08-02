package org.exemplo.aulasjpa.matricula;

import org.exemplo.aulasjpa.aluno.Aluno;
import org.exemplo.aulasjpa.aluno.AlunoRepository;
import org.exemplo.aulasjpa.turma.Turma;
import org.exemplo.aulasjpa.turma.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matriculas")
@Transactional
public class MatriculaController {

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private TurmaRepository turmaRepository;

  @PostMapping("/{alunoId}/{turmaId}")
  public ResponseEntity<Boolean> matricular(@PathVariable Long alunoId, @PathVariable Long turmaId) {
    Aluno aluno = alunoRepository.getById(alunoId);
    
    Turma turma = turmaRepository.getById(turmaId);

    aluno.setTurma(turma);

    return ResponseEntity.ok().build();
  }

//  @PutMapping("/{id}")
//  public ResponseEntity<Aluno> alterar(@PathVariable Long id, @RequestBody Aluno alunoAlterado) {
//    return alunoRepository.findById(id).map(alunoCadastrado -> {
//      alunoCadastrado.setNome(alunoAlterado.getNome());
//      return ResponseEntity.ok(alunoCadastrado);
//    }).orElse(ResponseEntity.notFound().build());
//  }
//
//  @DeleteMapping("/{id}")
//  public ResponseEntity<Aluno> apagar(@PathVariable Long id) {
//    Optional<Aluno> possivelAluno = alunoRepository.findById(id);
//    possivelAluno.ifPresent(alunoRepository::delete);
//
//    return possivelAluno.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//  }
}