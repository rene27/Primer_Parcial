package com.emergentes.controlador;

import com.emergentes.modelo.GestorTareas;
import com.emergentes.modelo.Tarea;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tarea objTarea = new Tarea();
        int id;
        int pos;
        String op = request.getParameter("op");
        
        if(op.equals("nuevo")){
            HttpSession ses = request.getSession();
            GestorTareas registro = (GestorTareas) ses.getAttribute("registro");
            objTarea.setId(registro.obtieneId());
            
            request.setAttribute("op", op);
            request.setAttribute("miTarea", objTarea);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }
        if(op.equals("modificar")){
            id = Integer.parseInt(request.getParameter("id"));
            HttpSession ses = request.getSession();
            GestorTareas registro = (GestorTareas) ses.getAttribute("registro");
            pos = registro.ubicarTarea(id);
            objTarea = registro.getLista().get(pos);
            
            request.setAttribute("op", op);
            request.setAttribute("miTarea", objTarea);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }
        if(op.equals("eliminar")){
            id = Integer.parseInt(request.getParameter("id"));
            HttpSession ses = request.getSession();
            GestorTareas registro = (GestorTareas) ses.getAttribute("registro");
            pos = registro.ubicarTarea(id);
            registro.eliminarTarea(pos);
            ses.setAttribute("registro", registro);
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tarea objTarea = new Tarea();
        int pos;
        String op = request.getParameter("op");
        
        if(op.equals("grabar")){
            objTarea.setId(Integer.parseInt(request.getParameter("id")));
            objTarea.setNombre(request.getParameter("nombre"));
            objTarea.setPeso(Double.parseDouble(request.getParameter("peso")));
            objTarea.setTalla(Double.parseDouble(request.getParameter("talla")));
            objTarea.setVacuna(request.getParameter("vacuna"));
            
            
            HttpSession ses = request.getSession();
            GestorTareas registro = (GestorTareas) ses.getAttribute("registro");
            
            String opg = request.getParameter("opg");
            if(opg.equals("nuevo")){
                registro.insertarTarea(objTarea);
            }
            else{
                pos = registro.ubicarTarea(objTarea.getId());
                registro.modificarTarea(pos, objTarea);
            }
            ses.setAttribute("registro", registro);
            response.sendRedirect("index.jsp");
        }
    }

}
