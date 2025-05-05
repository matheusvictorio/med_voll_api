package med.voll.api.pacientes;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;

public record DadosAtualizacaoPacientes(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
