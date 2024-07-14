package peliculasCac2024;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class UsuarioService {

    private Conexion conexion;

    public UsuarioService() {
        this.conexion = new Conexion();
    }

    public List<Usuario> getAllUsuario() throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM registro");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getDate("FechaNacimiento").toLocalDate(), // Convertir a LocalDate
                        rs.getString("pais")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario getUsuarioById(int id) throws SQLException, ClassNotFoundException {
        Usuario usuario = null;
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM registro WHERE id = ?")) {

            // Configurar el parámetro id en la consulta SQL
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Crear un nuevo objeto Usuario con los datos recuperados del ResultSet
                    usuario = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            rs.getDate("FechaNacimiento").toLocalDate(), // Convertir a LocalDate
                            rs.getString("pais")
                    );
                }
            }
        }
        return usuario;
    }

    public void addUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO registro (nombre, apellido, email, contraseña, FechaNacimiento, pais) VALUES (?, ?, ?, ?, ?, ?)")) {

            // Configurar los parámetros del PreparedStatement con los datos del usuario
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getContraseña());
            ps.setDate(5, Date.valueOf(usuario.getFechaNacimiento())); // Convertir LocalDate a java.sql.Date
            ps.setString(6, usuario.getPais());

            // Ejecutar la inserción
            ps.executeUpdate();
        }
    }

    public void updateUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE registro SET nombre = ?, apellido = ?, email = ?, contraseña = ?, FechaNacimiento = ?, pais = ? WHERE id = ?")) {

            // Configurar los parámetros del PreparedStatement con los datos actualizados del usuario
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getContraseña());
            ps.setDate(5, Date.valueOf(usuario.getFechaNacimiento())); // Convertir LocalDate a java.sql.Date
            ps.setString(6, usuario.getPais());
            ps.setInt(7, usuario.getId());

            // Ejecutar la actualización
            ps.executeUpdate();
        }
    }

    public void deleteUsuario(int id) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM registro WHERE id = ?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
