package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta json){
        if (json.idMedico() == null){
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(json.idMedico());
        if (!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com médico inativo.");
        }
    }
}
