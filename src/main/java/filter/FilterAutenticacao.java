package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;


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
		
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		
		String caminhoPastaSql = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
		
		File[] filesSql =  new File(caminhoPastaSql).listFiles();
		
			
			try {
				
				for (File file : filesSql) {
					
				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
					if (!arquivoJaRodado) {
						
						FileInputStream entradaArquivo = new FileInputStream(file);
						
						Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
						
						StringBuilder sql = new StringBuilder();
						
						while (lerArquivo.hasNext()) {
							
							sql.append(lerArquivo.next());
							sql.append("\n");
						}
						
						connection.prepareStatement(sql.toString()).execute();
						daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
						
						connection.commit();
						lerArquivo.close();
					}
				}
				
			} catch (Exception e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
		}
	}

}
