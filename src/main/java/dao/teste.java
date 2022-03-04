package dao;

/*calculo para paginação correta*/

public class teste {

	public static void main(String[] args) {
		
		Double cadastros = 12.0;
		
		Double porpagina =5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2 ;
		
		if (resto > 0) {
			pagina++;
		}		
		System.out.println(pagina.intValue());
	}

}
