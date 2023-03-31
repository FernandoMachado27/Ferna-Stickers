package ferna_stickers;

import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDaNasa implements ExtratorDeConteudo{
	
	public List<Conteudo> extraiConteudos(String json){
		// extrair só os dados interessantes (titulo, poster, classificação)
		
		var parser = new JsonParser();
		List<Map<String, String>> listaDeAtributos = parser.parse(json); // String, String pois tem a chave e o valor do json
		
		// transformando a lista de extração em lista de conteudos, mapeando lista em outra
		return listaDeAtributos.stream()
				.map(atributos -> new Conteudo(atributos.get("title"), atributos.get("url")))
				.toList(); 
	}

}
