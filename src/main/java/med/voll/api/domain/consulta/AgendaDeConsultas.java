package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoesCancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta json){
        if (!pacienteRepository.existsById(json.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if (json.idMedico() != null && !medicoRepository.existsById(json.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadores.forEach(v ->v.validar(json));

        var paciente = pacienteRepository.getReferenceById(json.idPaciente());
        var medico = escolherMedico(json);

        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, json.data(), null);


        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta json) {
        if (json.idMedico() != null){
            return medicoRepository.getReferenceById(json.idMedico());
        }

        if (json.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(json.especialidade(), json.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
