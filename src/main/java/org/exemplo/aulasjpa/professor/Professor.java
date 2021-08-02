package org.exemplo.aulasjpa.professor;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.exemplo.aulasjpa.disciplina.Disciplina;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "professores")
public class Professor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;
  
  @OneToMany(mappedBy = "professor")
  @JsonBackReference
  private Set<Disciplina> disciplinas;

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

  public Set<Disciplina> getDisciplinas() {
    return disciplinas;
  }

  public void setDisciplinas(Set<Disciplina> disciplinas) {
    this.disciplinas = disciplinas;
  }

}
