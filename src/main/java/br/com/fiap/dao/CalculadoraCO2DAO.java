package br.com.fiap.dao;

import br.com.fiap.exception.ErroBancoException;
import br.com.fiap.model.CalculadoraCO2;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CalculadoraCO2DAO {

    public CalculadoraCO2 inserir(CalculadoraCO2 calculo) {
        String sql = "INSERT INTO TB_CALCULO_CO2 "
                + "(ID_CALCULO, NM_USUARIO, TP_TRANSPORTE, NR_DISTANCIA_KM, NR_CO2_ECONOMIZADO, NR_PONTOS, VL_CASHBACK) "
                + "VALUES (SEQ_CALCULO_CO2.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_CALCULO"})) {

            ps.setString(1, calculo.getNome());
            ps.setString(2, calculo.getTipoTransporte());
            ps.setDouble(3, calculo.getDistanciaKm());
            ps.setDouble(4, calculo.getCo2Economizado());
            ps.setInt(5, calculo.getPontos());
            ps.setDouble(6, calculo.getCashback());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    calculo.setId(rs.getLong(1));
                }
            }
            return calculo;
        } catch (SQLException e) {
            throw new ErroBancoException("Erro ao inserir cálculo: " + e.getMessage(), e);
        }
    }

    public boolean alterar(CalculadoraCO2 calculo) {
        String sql = "UPDATE TB_CALCULO_CO2 SET NM_USUARIO = ?, TP_TRANSPORTE = ?, NR_DISTANCIA_KM = ?, "
                + "NR_CO2_ECONOMIZADO = ?, NR_PONTOS = ?, VL_CASHBACK = ? WHERE ID_CALCULO = ?";

        try (Connection con = ConnectionFactory.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, calculo.getNome());
            ps.setString(2, calculo.getTipoTransporte());
            ps.setDouble(3, calculo.getDistanciaKm());
            ps.setDouble(4, calculo.getCo2Economizado());
            ps.setInt(5, calculo.getPontos());
            ps.setDouble(6, calculo.getCashback());
            ps.setLong(7, calculo.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new ErroBancoException("Erro ao alterar cálculo: " + e.getMessage(), e);
        }
    }

    public boolean excluir(Long id) {
        String sql = "DELETE FROM TB_CALCULO_CO2 WHERE ID_CALCULO = ?";

        try (Connection con = ConnectionFactory.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new ErroBancoException("Erro ao excluir cálculo: " + e.getMessage(), e);
        }
    }

    public Optional<CalculadoraCO2> buscarPorId(Long id) {
        String sql = "SELECT * FROM TB_CALCULO_CO2 WHERE ID_CALCULO = ?";

        try (Connection con = ConnectionFactory.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new ErroBancoException("Erro ao buscar cálculo: " + e.getMessage(), e);
        }
    }

    public List<CalculadoraCO2> listarTodos() {
        String sql = "SELECT * FROM TB_CALCULO_CO2 ORDER BY ID_CALCULO";
        List<CalculadoraCO2> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.abrirConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new ErroBancoException("Erro ao listar cálculos: " + e.getMessage(), e);
        }
    }

    private CalculadoraCO2 mapear(ResultSet rs) throws SQLException {
        CalculadoraCO2 c = new CalculadoraCO2();
        c.setId(rs.getLong("ID_CALCULO"));
        c.setNome(rs.getString("NM_USUARIO"));
        c.setTipoTransporte(rs.getString("TP_TRANSPORTE"));
        c.setDistanciaKm(rs.getDouble("NR_DISTANCIA_KM"));
        c.setCo2Economizado(rs.getDouble("NR_CO2_ECONOMIZADO"));
        c.setPontos(rs.getInt("NR_PONTOS"));
        c.setCashback(rs.getDouble("VL_CASHBACK"));
        return c;
    }
}