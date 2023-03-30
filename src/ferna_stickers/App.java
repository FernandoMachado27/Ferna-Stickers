package ferna_stickers;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws Exception {

		// fazer uma conexão HTTP e buscar os top 250 filmes
		
		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
//		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
		URI endereco = URI.create(url); // endereco
		var client = HttpClient.newHttpClient(); // cliente
		var request = HttpRequest.newBuilder(endereco).GET().build(); // request
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString()); // response vai ser retornada String
		String body = response.body();
		
		// extrair só os dados interessantes (titulo, poster, classificação)
		
		var parser = new JsonParser();
		List<Map<String, String>> listaDeFilmes = parser.parse(body);
		
		// exibir e manipular os dados
		
		var diretorio = new File("figurinhas/");
		diretorio.mkdir(); // cria o diretório se não existir
		
		var gerador = new GeradorDeFigurinhas();
		for (int index = 0; index < 3; index++) {
			var filme = listaDeFilmes.get(index);
			
			String urlImagem = filme.get("image");
			String titulo = filme.get("title");
			double classificacao = Double.parseDouble(filme.get("imDbRating"));
			
			String textoFigurinha;
			if (classificacao >= 8.0) {
				textoFigurinha = "TOPZAO";
			} else {
				textoFigurinha = "HMMMM...";
			}
			
			InputStream inputStream = new URL(urlImagem).openStream();
			
			String nomeDoArquivo = "figurinhas/" + titulo + ".png";
			
			gerador.cria(inputStream, nomeDoArquivo, textoFigurinha);
			
			System.out.println("\u001b[1mTitulo:\u001b[m " + titulo); 
			System.out.println("");
		}
	}

}
