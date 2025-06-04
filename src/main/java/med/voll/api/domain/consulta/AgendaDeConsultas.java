package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta json){
        if (!pacienteRepository.existsById(json.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if (json.idMedico() != null && !medicoRepository.existsById(json.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        var paciente = pacienteRepository.getReferenceById(json.idPaciente());
        var medico = escolherMedico(json);

        var consulta = new Consulta(null, medico, paciente, json.data(), null);


        consultaRepository.save(consulta);
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

    public void cancelar(@Valid DadosCancelamentoConsulta json) {
        if (!consultaRepository.existsById(json.idConsulta())){
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(json.idConsulta());
        consulta.cancelar(json.motivo());
    }
}
