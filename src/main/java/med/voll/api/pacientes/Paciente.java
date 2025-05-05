package med.voll.api.pacientes;

import jakarta.persistence.*;
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

    public Paciente(DadosCadastroPaciente json) {
        this.nome = json.nome();
        this.email = json.email();
        this.telefone = json.telefone();
        this.cpf = json.cpf();
        this.endereco = new Endereco(json.endereco());
    }
}
