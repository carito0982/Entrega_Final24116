package peliculasCac2024;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/registro/*")
public class UsuarioServlet extends HttpServlet {
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Usuario> usuarios = usuarioService.getAllUsuario();
                String json = objectMapper.writeValueAsString(usuarios);
                resp.setContentType("application/json");
                resp.getWriter().write(json);
            } else {
                String[] pathParts = pathInfo.split("/");
                int id = Integer.parseInt(pathParts[1]);
                Usuario usuario = usuarioService.getUsuarioById(id);
                if (usuario != null) {
                    String json = objectMapper.writeValueAsString(usuario);
                    resp.setContentType("application/json");
                    resp.getWriter().write(json);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Usuario usuario = objectMapper.readValue(req.getReader(), Usuario.class);
            usuarioService.addUsuario(usuario);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Usuario usuario = objectMapper.readValue(req.getReader(), Usuario.class);
            usuarioService.updateUsuario(usuario);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.split("/").length > 1) {
                int id = Integer.parseInt(pathInfo.split("/")[1]);
                usuarioService.deleteUsuario(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
