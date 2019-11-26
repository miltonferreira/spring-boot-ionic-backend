package com.jotonferreira.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jotonferreira.cursomc.services.exceptions.FileException;

@Service
public class ImageService {
	// classe usada para ajustar imagem e preparar para envio pro S3

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename()); // extrai a extensão da imagem
		
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}
		
		
		try {
			
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream()); // tenta ler um arquivo de imagem
			
			if ("png".equals(ext)) {
				img = pngToJpg(img); // tenta converte png para jpg
			}
			return img;
			
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
		
	}
	
	// converte png para jpg
	public BufferedImage pngToJpg(BufferedImage img) {
		
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null); // se o fundo por transparente, substitui para branco
		
		return jpgImage;
	}
	
	// retorna um InputStream apartir do BufferedImage, o metodo que faz upload do S3 usa um InputStream para enviar a imagem
	public InputStream getInputStream(BufferedImage img, String extension) {
		
		try {
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			ImageIO.write(img, extension, os);
			
			return new ByteArrayInputStream(os.toByteArray());
			
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	// corta imagem retornando ela
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth(); // verifica qual é o menor tamanho se é altura ou largura para ficar com o menor tamanho
		
		// recorta a imagem
		return Scalr.crop(
			sourceImg, 
			(sourceImg.getWidth()/2) - (min/2), 
			(sourceImg.getHeight()/2) - (min/2), 
			min, 
			min);		
	}
	
	//redimensiona imagem
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}
	
}
