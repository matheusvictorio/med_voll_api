package med.voll.api.domain.pacientes;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends org.springframework.data.jpa.repository.JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            select p.ativo from Paciente p
            where p.id = :id
            """)
    Boolean findAtivoById(@NotNull Long id);
}
