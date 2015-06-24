package controladores.seguridad;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

public class FiltroJR implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma","no-cache");
        resp.setDateHeader("Expires", -1);
        Object userInSession = session.getAttribute("usuarioLogeado");
        if(userInSession == null){
            System.out.println("Session nula...");
            request.setAttribute("errorAccesos", "Sesion está nula.");
            req.getRequestDispatcher("/faces/IU_IngresarSistema.xhtml").forward(request, response);
            return;
        }
        System.out.println("OOOOki");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        
    }
    
}
