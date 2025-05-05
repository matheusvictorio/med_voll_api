package med.voll.api.pacientes;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import med.voll.api.endereco.Endereco;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    String email;
    String telefone;
    String cpf;
    @Embedded
    Endereco endereco;

    private boolean ativo;

    public Paciente(DadosCadastroPaciente json) {
        this.ativo = true;
        this.nome = json.nome();
        this.email = json.email();
        this.telefone = json.telefone();
        this.cpf = json.cpf();
        this.endereco = new Endereco(json.endereco());
    }

    public void atualizarDados(@Valid DadosAtualizacaoPacientes json) {
        if (json.nome() != null) {
            this.nome = json.nome();
        }
        if (json.telefone() != null) {
            this.telefone = json.telefone();
        }
        if (json.endereco() != null) {
            this.endereco.atualizarInformacoes(json.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
