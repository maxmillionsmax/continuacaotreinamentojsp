<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>



<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>


<body>
	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
							<div class="page-body">
							<div class="row">
							<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
							<div class="card">
							<div class="card-block">
							<h4 class="sub-title">Cad. Telefone</h4>

							<form class="form-material" method="post" action="<%=request.getContextPath()%>/ServletTelefone"
							id="formFone">
							<div class="form-group form-default form-static-label">
							<input type="text" name="id" id="id"
							value="${modelLogin.id}" class="form-control"
							required="" readonly="readonly"> <span
							class="form-bar"></span> <label class="float-label">ID:</label>
							</div>

							<div class="form-group form-default form-static-label">
							<input readonly="readonly" type="text" name="nome" id="nome"
							autocomplete="off" value="${modelLogin.nome}"
							class="form-control" required="required"> <span
							class="form-bar"></span> <label class="float-label">Nome:</label>
							</div>
							
							<div class="form-group form-default form-static-label">
							<input type="text" name="numero" id="numero" class="form-control" required="required"> 
							<span class="form-bar"></span> <label class="float-label">Numero:</label>
							</div>
							<button type="submit" class="btn btn-success waves-effect waves-light">salvar</button>
							

							</form>
							</div>
							</div>
							</div>
						    </div>
							<span id="msg">${msg}</span>
							
							<div style="height: 300px; overflow: scroll;">
		<table class="table table-light" id="tabelaResultadoview">
		  <thead>
		    <tr>
		      <th scope="col">ID</th>
		      <th scope="col">Numero:</th>
		      <th scope="col">Excluir</th>
		    </tr>
		  </thead>
		  <tbody>
		    <c:forEach items="${modelTelefones}" var="f">
		    	<tr>
				<td><c:out value="${f.id}"></c:out></td>
				<td><c:out value="${f.numero}"></c:out></td>
				<td><a href="<%= request.getContextPath()%>/ServletTelefone?acao=excluir&id=${f.id}&userpai=${modelLogin.id}"
				 class="btn btn-success waves-effect waves-light">Excluir</a></td>
		    	</tr>
		    </c:forEach>
		  </tbody>
		</table>		
	</div>  
							
						

							</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- Required Jquery -->
	<jsp:include page="javascriptfile.jsp"></jsp:include>
</body>

</html>
