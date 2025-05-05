package med.voll.api.pacientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PacienteRepository extends org.springframework.data.jpa.repository.JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);
}
