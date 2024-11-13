package com.example.crud1.service;

import com.example.crud1.dto.Clientedto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Clientedto> obtenerClientes(){
        String sql="SELECT id, nombre, correo, telefono FROM cliente";
        return jdbcTemplate.query(sql,(rs, rowNum) ->
                new Clientedto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                ));
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Clientedto registrarCliente(Clientedto clientedto){
        String sql = "INSERT INTO Cliente (nombre, correo, telefono) VALUES (?,?,?)";
        jdbcTemplate.update(sql,clientedto.getNombre(),clientedto.getCorreo(),clientedto.getTelefono());
        sql="select id from cliente where nombre=?";
        clientedto.setId(jdbcTemplate.queryForObject(sql, Integer.class,clientedto.getNombre()));
        return clientedto;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Clientedto actualizarCliente(Clientedto clientedto){
        String sql = "UPDATE cliente SET nombre = ?, correo = ?, telefono = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, clientedto.getNombre(), clientedto.getCorreo(), clientedto.getTelefono(), clientedto.getId());
        if (rows > 0) {
            return clientedto;
        } else {
            throw new RuntimeException("Cliente no encontrado o error en la actualización");
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public String eliminarCliente(int id){
        String sql = "DELETE FROM cliente WHERE id = ?";
        jdbcTemplate.update(sql,id);
        return "El cliente ha sido eliminado";
    }
}
