package br.com.fiap.bo;

import br.com.fiap.dao.CalculadoraCO2DAO;
import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.exception.ValidacaoException;
import br.com.fiap.model.CalculadoraCO2;
import br.com.fiap.model.Metro;
import br.com.fiap.model.Onibus;
import br.com.fiap.model.Transporte;
import br.com.fiap.model.Trem;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Service
public class CalculadoraCO2BO {

    private static final double DISTANCIA_MAXIMA_KM = 1000;
    private static final int TAMANHO_MAXIMO_NOME = 100;

    private final CalculadoraCO2DAO dao;

    public CalculadoraCO2BO(CalculadoraCO2DAO dao) {
        this.dao = dao;
    }

    public List<CalculadoraCO2> listar() {
        return dao.listarTodos();
    }

    public CalculadoraCO2 buscar(Long id) {
        return dao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cálculo não encontrado: id " + id));
    }

    public CalculadoraCO2 cadastrar(CalculadoraCO2 calculo) {
        prepararCalculo(calculo);
        return dao.inserir(calculo);
    }

    public CalculadoraCO2 atualizar(Long id, CalculadoraCO2 calculo) {
        buscar(id); // lança 404 se não existir
        calculo.setId(id);
        prepararCalculo(calculo);
        dao.alterar(calculo);
        return calculo;
    }

    public void excluir(Long id) {
        if (!dao.excluir(id)) {
            throw new RecursoNaoEncontradoException("Cálculo não encontrado: id " + id);
        }
    }

    // Valida os dados e preenche CO2, pontos e cashback
    private void prepararCalculo(CalculadoraCO2 calculo) {
        validarNome(calculo.getNome());
        String tipo = normalizarTransporte(calculo.getTipoTransporte());
        validarDistancia(calculo.getDistanciaKm());

        calculo.setNome(calculo.getNome().trim());
        calculo.setTipoTransporte(tipo);

        calculo.calcularCO2(criarTransporte(tipo));
        calculo.calcularPontos();
        calculo.calcularCashback();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ValidacaoException("O username é obrigatório.");
        }
        if (nome.trim().length() > TAMANHO_MAXIMO_NOME) {
            throw new ValidacaoException("O username deve ter no máximo " + TAMANHO_MAXIMO_NOME + " caracteres.");
        }
        if (nome.trim().matches("\\d+([.,]\\d+)?")) {
            throw new ValidacaoException("O username não pode ser um número.");
        }
    }

    private void validarDistancia(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new ValidacaoException("A distância deve ser maior que zero.");
        }
        if (distanciaKm > DISTANCIA_MAXIMA_KM) {
            throw new ValidacaoException("A distância máxima por trajeto é " + (int) DISTANCIA_MAXIMA_KM + " km.");
        }
    }

    // "Ônibus", "onibus", " ÔNIBUS " -> "ONIBUS"
    private String normalizarTransporte(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new ValidacaoException("O tipo de transporte é obrigatório (Ônibus, Metrô ou Trem).");
        }
        String semAcento = Normalizer.normalize(tipo.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        String normalizado = semAcento.toUpperCase(Locale.ROOT);

        if (!normalizado.equals("ONIBUS") && !normalizado.equals("METRO") && !normalizado.equals("TREM")) {
            throw new ValidacaoException("Transporte inválido. Use apenas Ônibus, Metrô ou Trem.");
        }
        return normalizado;
    }

    private Transporte criarTransporte(String tipo) {
        return switch (tipo) {
            case "ONIBUS" -> new Onibus();
            case "METRO" -> new Metro();
            default -> new Trem();
        };
    }
}