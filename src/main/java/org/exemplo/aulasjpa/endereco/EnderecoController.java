package org.exemplo.aulasjpa.endereco;

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
@RequestMapping("/enderecos")
@Transactional
public class EnderecoController {

  @Autowired
  private EnderecoRepository enderecoRepository;

  @GetMapping
  public List<Endereco> listar() {
    return enderecoRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Endereco> recuperarPorId(@PathVariable Long id) {
    return enderecoRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Endereco> cadastrar(@RequestBody Endereco novoEndereco, UriComponentsBuilder uriBuilder) {
    Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);

    URI location = uriBuilder.path("/enderecos/{id}").buildAndExpand(novoEndereco.getId()).toUri();

    return ResponseEntity.created(location).body(enderecoSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Endereco> alterar(@PathVariable Long id, @RequestBody Endereco enderecoAlterado) {
    return enderecoRepository.findById(id).map(enderecoCadastrado -> {
      enderecoCadastrado.setLogradouro(enderecoAlterado.getLogradouro());
      return ResponseEntity.ok(enderecoCadastrado);
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Endereco> apagar(@PathVariable Long id) {
    Optional<Endereco> possivelEndereco = enderecoRepository.findById(id);
    possivelEndereco.ifPresent(enderecoRepository::delete);

    return possivelEndereco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}