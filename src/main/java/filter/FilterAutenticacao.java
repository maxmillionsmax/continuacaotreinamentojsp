package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterAutenticacao
 */
@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao implements Filter {

	private static Connection connection;
    /**
     * Default constructor. 
     */
    public FilterAutenticacao() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
	   try {
		   HttpServletRequest req = (HttpServletRequest) request;
		   HttpSession session = req.getSession();
		   
		   String usuarioLogado = (String) session.getAttribute("usuario");
		   
		   String urlParaAutenticar = req.getServletPath();
		   
		   if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")
				   && !urlParaAutenticar.equalsIgnoreCase("/ServletLogin")){
			   
			   RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url="+urlParaAutenticar);
			   request.setAttribute("msg", "POR FAVOR REALIZE O LOGIN");
			   redireciona.forward(request, response);
			   return;			
		   }else {
			   chain.doFilter(request, response);
		   }
		   
		   connection.commit();
		   
		
	} catch (Exception e) {
		e.printStackTrace();
		
		RequestDispatcher rediredcionar = request.getRequestDispatcher("erro.jsp");
		request.setAttribute("msg", e.getMessage());
		rediredcionar.forward(request, response);
		
		
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 }			
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		connection = SingleConnectionBanco.getConnection();
		
	}

}
