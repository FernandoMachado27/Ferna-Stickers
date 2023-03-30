package ferna_stickers;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws Exception {

		// fazer uma conexão HTTP e buscar os top 250 filmes
		
//		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
//		ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();
		
		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/NASA-APOD.json";
		ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();
		
		var http = new ClienteHttp();
		String json = http.buscaDados(url);
		
		// extrair só os dados interessantes (titulo, poster, classificação)
		
		List<Conteudo> conteudos = extrator.extraiConteudos(json);
		
		// exibir e manipular os dados
		
		var diretorio = new File("figurinhas/");
		diretorio.mkdir(); // cria o diretório se não existir
		
		var gerador = new GeradorDeFigurinhas();
		for (int i = 0; i < 3; i++) {
			Conteudo conteudo = conteudos.get(i);
			
			String textoFigurinha = "TOPZAO";
			InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
			String nomeDoArquivo = "figurinhas/" + conteudo.titulo() + ".png";
			
			gerador.cria(inputStream, nomeDoArquivo, textoFigurinha);
			
			System.out.println("\u001b[1mTitulo:\u001b[m " + conteudo.titulo()); 
			System.out.println("");
		}
	}

}
