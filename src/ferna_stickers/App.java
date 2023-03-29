package ferna_stickers;

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
		URI endereco = URI.create(url); // endereco
		var client = HttpClient.newHttpClient(); // cliente
		var request = HttpRequest.newBuilder(endereco).GET().build(); // request
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString()); // response vai ser retornada String
		String body = response.body();
		
		// extrair só os dados interessantes (titulo, poster, classificação)
		
		var parser = new JsonParser();
		List<Map<String, String>> listaDeFilmesList = parser.parse(body);
		
		// exibir e manipular os dados
		
		var gerador = new GeradorDeFigurinhas();
		for (Map<String, String> filme : listaDeFilmesList) {
			String urlImagem = filme.get("image");
			String titulo = filme.get("title");
			
			InputStream inputStream = new URL(urlImagem).openStream();
			
			String nomeDoArquivo = titulo + ".png";
			
			gerador.cria(inputStream, nomeDoArquivo);
			
			System.out.println("\u001b[1mTitulo:\u001b[m " + titulo); 
			System.out.println("");
		}
	}

}
