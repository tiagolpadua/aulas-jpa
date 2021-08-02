package org.exemplo.aulasjpa.turma;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.exemplo.aulasjpa.aluno.Aluno;
import org.exemplo.aulasjpa.disciplina.Disciplina;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "turmas")
public class Turma {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String descricao;

  @OneToMany(mappedBy = "turma")
  @JsonBackReference
  private Set<Aluno> alunos;

  @ManyToMany
  @JoinTable(name = "turma_disciplina", //
    joinColumns = @JoinColumn(name = "turma_id"), //
    inverseJoinColumns = @JoinColumn(name = "disciplina_id")) //
  @JsonManagedReference
  Set<Disciplina> disciplinas;

  public Long getId() {
    return id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Set<Aluno> getAlunos() {
    return alunos;
  }

  public void setAlunos(Set<Aluno> alunos) {
    this.alunos = alunos;
  }

  public Set<Disciplina> getDisciplinas() {
    return disciplinas;
  }

  public void setDisciplinas(Set<Disciplina> disciplinas) {
    this.disciplinas = disciplinas;
  }

}
