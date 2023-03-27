package ferna_stickers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {

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
		
		for (Map<String, String> filme : listaDeFilmesList) {
			System.out.println(filme.get("title"));
			System.out.println(filme.get("rank"));
			System.out.println(filme.get("year"));
			System.out.println();
		}
	}

}
