package org.exemplo.aulasjpa.aluno;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.exemplo.aulasjpa.endereco.Endereco;
import org.exemplo.aulasjpa.endereco.EnderecoRepository;
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
@RequestMapping("/alunos")
@Transactional
public class AlunoController {

  @Autowired
  private AlunoRepository alunoRepository;
  
  @Autowired
  private EnderecoRepository enderecoRepository;

  @GetMapping
  public List<Aluno> listar() {
    return alunoRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Aluno> recuperarPorId(@PathVariable Long id) {
    return alunoRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno novoAluno, UriComponentsBuilder uriBuilder) {
    Aluno alunoSalvo = alunoRepository.save(novoAluno);

    URI location = uriBuilder.path("/alunos/{id}").buildAndExpand(novoAluno.getId()).toUri();

    return ResponseEntity.created(location).body(alunoSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Aluno> alterar(@PathVariable Long id, @RequestBody Aluno alunoAlterado) {
    return alunoRepository.findById(id).map(alunoCadastrado -> {
      alunoCadastrado.setNome(alunoAlterado.getNome());
      return ResponseEntity.ok(alunoCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }
  
  @PostMapping("/{id}/endereco")
  public ResponseEntity<Aluno> incluirEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
    return alunoRepository.findById(id).map(alunoCadastrado -> {
      alunoCadastrado.setEndereco(endereco);
      return ResponseEntity.ok(alunoCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Aluno> apagar(@PathVariable Long id) {
    Optional<Aluno> possivelAluno = alunoRepository.findById(id);
    possivelAluno.ifPresent(alunoRepository::delete);

    return possivelAluno.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
  
  @DeleteMapping("/{id}/endereco")
  public ResponseEntity<Aluno> excluirEndereco(@PathVariable Long id) {
    Optional<Aluno> possivelAluno = alunoRepository.findById(id);
    possivelAluno.ifPresent(aluno -> {
      Endereco e = aluno.getEndereco(); 
      enderecoRepository.delete(e);
      aluno.setEndereco(null);
    });
    return possivelAluno.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}