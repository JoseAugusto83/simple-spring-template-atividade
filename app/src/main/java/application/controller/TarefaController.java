package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Tarefa;
import application.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    TarefaRepository tarefaRepo;
    

    @PostMapping
    public Tarefa createTarefa(@RequestBody Tarefa body){
        return tarefaRepo.save(body);
    }

    @GetMapping
    public Iterable<Tarefa> listTarefa(){
        return tarefaRepo.findAll();
    }

    @GetMapping("/{id}")
    public Tarefa getTarefa(@PathVariable Long id){
        Optional<Tarefa> resultado = tarefaRepo.findById(id);

        if(resultado.isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tarefa não encontrada"
            );
        };
        return resultado.get();
    }

    @PutMapping("/{id}")
    public Tarefa updateTarefa(@PathVariable Long id, @RequestBody Tarefa body){
        Optional<Tarefa> resultado = tarefaRepo.findById(id);

        if(resultado.isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tarefa não encontrada"
            );
        };

        if(body.getDescricao().isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Descrição invalida"
            );
        };

        resultado.get().setConcluido(body.isConcluido());
        resultado.get().setDescricao(body.getDescricao());
        return tarefaRepo.save(resultado.get());
    }

    @DeleteMapping("/{id}")
    public void deleteTarefa(@PathVariable Long id){;
        if(!tarefaRepo.existsById(id)){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tarefa não encontrada"
            );
        }
        tarefaRepo.deleteById(id);
    }
}
