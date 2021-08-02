package org.exemplo.aulasjpa.materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/{id}")
//    public ResponseEntity<Marca> marcaPorId(@PathVariable Long id) {
//        return marcaRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Marca> cadastraMarca(@Valid @RequestBody Marca novaMarca, UriComponentsBuilder uriBuilder) {
//        Marca marcaSalva = marcaRepository.save(novaMarca);
//
//        URI location = uriBuilder.path("/marcas/{id}")
//                .buildAndExpand(marcaSalva.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(marcaSalva);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Marca> alteraNome(@PathVariable Long id, @Valid @RequestBody Marca marcaAlterada) {
//        return marcaRepository.findById(id)
//                .map(marcaCadastrada -> {
//                    marcaCadastrada.setNome(marcaAlterada.getNome());
//                    return ResponseEntity.ok(marcaCadastrada);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Marca> deletaMarca(@PathVariable Long id) {
//        Optional<Marca> possivelMarca = marcaRepository.findById(id);
//        possivelMarca.ifPresent(marcaRepository::delete);
//
//        return possivelMarca.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}