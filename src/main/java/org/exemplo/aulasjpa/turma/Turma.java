package org.exemplo.aulasjpa.turma;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.exemplo.aulasjpa.aluno.Aluno;

@Entity
@Table(name = "turmas")
public class Turma {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String descricao;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
  private Set<Aluno> alunos = new HashSet<Aluno>();

  public Long getId() {
    return id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
