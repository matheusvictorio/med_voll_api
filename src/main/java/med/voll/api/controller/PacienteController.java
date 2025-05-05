package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente json){
        pacienteRepository.save(new Paciente(json));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = "nome") Pageable pageable){
        return pacienteRepository.findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPacientes json){
        var paciente = pacienteRepository.getReferenceById(json.id());
        paciente.atualizarDados(json);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }
}
