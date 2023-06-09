package ferna_stickers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo{

	public List<Conteudo> extraiConteudos(String json){
		// extrair só os dados interessantes (titulo, poster, classificação)
		
		var parser = new JsonParser();
		List<Map<String, String>> listaDeAtributos = parser.parse(json); // String, String pois tem a chave e o valor do json
		
		List<Conteudo> conteudos = new ArrayList<>();
		
		// popular a lista de conteudos
		
		for (Map<String, String> atributos : listaDeAtributos) { // para cada item da lista do Json, cria novo conteudo
			String titulo = atributos.get("title");
			String urlImagem = atributos.get("image").replaceAll("(@+)(.*).jpg$", "$1.jpg"); // regex para pegar imagem grande
			var conteudo = new Conteudo(titulo, urlImagem);
			
			conteudos.add(conteudo);
		}
		
		return conteudos;
	}
	
}
