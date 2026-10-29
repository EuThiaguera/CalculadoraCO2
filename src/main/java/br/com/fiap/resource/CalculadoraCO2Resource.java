package br.com.fiap.resource;

import br.com.fiap.bo.CalculadoraCO2BO;
import br.com.fiap.model.CalculadoraCO2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/calculos")
public class CalculadoraCO2Resource {

    private final CalculadoraCO2BO bo;

    public CalculadoraCO2Resource(CalculadoraCO2BO bo) {
        this.bo = bo;
    }

    @GetMapping
    public ResponseEntity<List<CalculadoraCO2>> listar() {
        return ResponseEntity.ok(bo.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalculadoraCO2> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(bo.buscar(id));
    }

    @PostMapping
    public ResponseEntity<CalculadoraCO2> cadastrar(@RequestBody CalculadoraCO2 calculo) {
        CalculadoraCO2 criado = bo.cadastrar(calculo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.getId())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalculadoraCO2> atualizar(@PathVariable Long id,
                                                    @RequestBody CalculadoraCO2 calculo) {
        return ResponseEntity.ok(bo.atualizar(id, calculo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        bo.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}