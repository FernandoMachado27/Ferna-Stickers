package ferna_stickers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorDeFigurinhas {
	
	public void cria(InputStream inputStream, String nomeArquivo, String texto) throws Exception {
		
		// leitura da imagem 
		
		BufferedImage imagemOriginal = ImageIO.read(inputStream); // ler a imagem original
		
		// cria nova imagem em memória em memória com transparência e com tamanho novo
		
		int largura = imagemOriginal.getWidth();
		int altura = imagemOriginal.getHeight();
		int novaAltura = altura + 200; // altura da imagem original + 200 pixels
		
		BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT); // criar nova imagem vazia com fundo transparente 
		
		// copiar a imagem original para nova imagem (em memória)
		
		Graphics2D graphics = (Graphics2D) novaImagem.getGraphics(); // graphics é uma "caneta"
		graphics.drawImage(imagemOriginal, 0, 0, null); // colocar a imagem ori na nova na posição 0x 0y
		
		// configurar a fonte
		
		var fonte = new Font("Impact", Font.BOLD, 80); // fonte, estilo, tamanho
		graphics.setColor(Color.YELLOW);
		graphics.setFont(fonte);
		
		// escrever uma frase na nova imagem
		
//		String texto = "TOPZAO";
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
		int larguraTexto = (int) retangulo.getWidth();
		int posicaoTextoX = (largura - larguraTexto)/2; // 4 linhas para centralizar o texto
		int posicaoTextoY = novaAltura - 100;
		graphics.drawString(texto, posicaoTextoX, posicaoTextoY); // escrever o texto
		
		FontRenderContext fontRenderContext = graphics.getFontRenderContext();
		var textLayout = new TextLayout(texto, fonte, fontRenderContext);
		
		Shape outline = textLayout.getOutline(null);
		AffineTransform transform = graphics.getTransform();
		transform.translate(posicaoTextoX, posicaoTextoY);
		graphics.setTransform(transform);
		
		var outlineStroke = new BasicStroke(largura * 0.004f);
		graphics.setStroke(outlineStroke);
		
		graphics.setColor(Color.black);
		graphics.draw(outline);
		graphics.setClip(outline); // 11 linhas para o contorno da palavra
		
		// escrever a nova imagem em um arquivo
		
		ImageIO.write(novaImagem, "png", new File(nomeArquivo));
		
	}

}
