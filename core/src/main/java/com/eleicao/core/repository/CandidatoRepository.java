package com.eleicao.core.repository;

import com.eleicao.core.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findAllByOrderByQuantidadeVotosDesc();
    boolean existsByNome(String nome);
    Candidato findByNome(String nome);

    @Query("SELECT SUM(c.quantidadeVotos) FROM Candidato c")
    Long sumAllVotes();
}
