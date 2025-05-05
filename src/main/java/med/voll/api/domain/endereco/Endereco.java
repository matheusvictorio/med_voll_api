package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String complemento;
    private String numero;

    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
        this.complemento = endereco.complemento();
        this.numero = endereco.numero();
    }

    public void atualizarInformacoes(DadosEndereco json) {
        if (json.logradouro() != null) {
            this.logradouro = json.logradouro();
        }
        if (json.bairro() != null) {
            this.bairro = json.bairro();
        }
        if (json.cep() != null) {
            this.cep = json.cep();
        }
        if (json.cidade() != null) {
            this.cidade = json.cidade();
        }
        if (json.uf() != null) {
            this.uf = json.uf();
        }
        if (json.complemento() != null) {
            this.complemento = json.complemento();
        }
        if (json.numero() != null) {
            this.numero = json.numero();
        }
    }
}
