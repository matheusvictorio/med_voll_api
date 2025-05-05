package med.voll.api.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import med.voll.api.endereco.Endereco;


@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;

    private boolean ativo;


    public Medico(DadosCadastroMedico json) {
        this.ativo = true;
        this.nome = json.nome();
        this.email = json.email();
        this.telefone = json.telefone();
        this.crm = json.crm();
        this.especialidade = json.especialidade();
        this.endereco = new Endereco(json.endereco());
    }

    public void atualizarDados(@Valid DadosAtualizacaoMedico json) {
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
