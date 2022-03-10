package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {

	private Connection connection;
	

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento,rendamensal)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.setLong(5, userLogado);
			statement.setString(6, objeto.getPerfil());
			statement.setString(7, objeto.getSexo());
			statement.setString(8, objeto.getCep());
			statement.setString(9, objeto.getLogradouro());
			statement.setString(10, objeto.getBairro());
			statement.setString(11, objeto.getLocalidade());
			statement.setString(12, objeto.getUf());
			statement.setString(13, objeto.getNumero());
			statement.setDate(14, objeto.getDataNascimento());
			statement.setDouble(15, objeto.getRendamensal());
			

			statement.execute();
			connection.commit();
			
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser = ?, extensaofotouser=? where login =?";
				statement = connection.prepareStatement(sql);
				statement.setString(1, objeto.getFotouser());
				statement.setString(2, objeto.getExtensaofotouser());
				statement.setString(3, objeto.getLogin());
				

				statement.execute();
				connection.commit();
				
			}
			
		} else {

			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? ,perfil = ?, sexo = ?, cep = ?, logradouro = ?, bairro = ?, localidade = ?, uf = ?, numero = ?, datanascimento =?, rendamensal =? WHERE id = " + objeto.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.setString(5, objeto.getPerfil());
			statement.setString(6, objeto.getSexo());
			statement.setString(7, objeto.getCep());
			statement.setString(8, objeto.getLogradouro());
			statement.setString(9, objeto.getBairro());
			statement.setString(10, objeto.getLocalidade());
			statement.setString(11, objeto.getUf());
			statement.setString(12, objeto.getNumero());
			statement.setDate(13, objeto.getDataNascimento());
			statement.setDouble(14, objeto.getRendamensal());

			statement.executeUpdate();
			connection.commit();
			
			if (objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser = ?, extensaofotouser=? where id = ?";
				statement = connection.prepareStatement(sql);
				statement.setString(1, objeto.getFotouser());
				statement.setString(2, objeto.getExtensaofotouser());
				statement.setLong(3, objeto.getId());
				

				statement.execute();
				connection.commit();
				
			}
			
		}

		return this.consultarUsuario(objeto.getLogin(),userLogado);

	}
	

	public List<ModelLogin> consultaUsuarioListOffSet(String nome,Long userLogado, Integer offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);


		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		
		return retorno;
	}
	
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado +" order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}
		
		return retorno;
	}
	
	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = "select count(1) as total from model_login where usuario_id = "+userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();

		Double cadastros = resultado.getDouble("total");
		
		Double porpagina =5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2 ;
		
		if (resto > 0) {
			pagina++;
		}	
		return pagina.intValue();
	}
	
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));

			retorno.add(modelLogin);

		}

		return retorno;

	}
	
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ? ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));

			retorno.add(modelLogin);

		}

		return retorno;

	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado +" limit 5;";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}

		return retorno;

	}

	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {

		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

        ResultSet resultado = statement.executeQuery();
		
		resultado.next();

		Double cadastros = resultado.getDouble("total");
		
		Double porpagina =5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2 ;
		
		if (resto > 0) {
			pagina++;
		}	
		return pagina.intValue();
	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);

		}

		return retorno;

	}

	public ModelLogin consultarUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login
				+ "');";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
		}
		return modelLogin;
	}
	
	public ModelLogin consultarUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login
				+ "') and useradmin is false;";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}
		return modelLogin;
	}
	
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + login
				+ "') and useradmin is false and usuario_id =" + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}
		return modelLogin;
	}
	
	public ModelLogin consultarUsuarioID(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false; ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1,id);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}
		return modelLogin;
	}

	public ModelLogin consultarUsuarioID(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id =? and useradmin is false and usuario_id = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}
		return modelLogin;
	}

	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('" + login + "');";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		resultado.next();
		return resultado.getBoolean("existe");
	}

	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
	}

	public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
		
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
						
		String sql = "select * from telefone where usuario_pai_id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, idUserPai);
		
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultarUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultarUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}
}
