package org.exemplo.aulasjpa.disciplina;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.exemplo.aulasjpa.turma.Turma;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "disciplinas")
public class Disciplina {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;
  
  @ManyToMany(mappedBy = "disciplinas")
  @JsonBackReference
  private Set<Turma> turmas;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

}
