package com.jotonferreira.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jotonferreira.cursomc.services.exceptions.FileException;

@Service
public class S3Service {
	// classe que envia arquivos para o bucket da amazonS3
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class); // mostra no log mensagem de erros

	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName; // pega caminho do bucket
	
	// MultipartFile é um arquivo que se envia na requisição do endPoint
	public URI uploadFile(MultipartFile multipartFile) {
		
		try {
			
			String fileName = multipartFile.getOriginalFilename(); // extrai o nome do arquivo que foi enviado
			InputStream is = multipartFile.getInputStream(); // encapsula o processamento de leitura do arquivo
			String contentType = multipartFile.getContentType(); // recebe o tipo do arquivo que foi enviado
			
			return uploadFile(is, fileName, contentType);
						
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		} 
	
	}
	
	// MultipartFile é um arquivo que se envia na requisição do endPoint
	public URI uploadFile(InputStream is, String fileName, String contentType) {
		
		try {
			
			ObjectMetadata meta = new ObjectMetadata();
			
			meta.setContentType(contentType);
			
			LOG.info("Iniciando upload:");
			
			s3client.putObject(bucketName, fileName, is, meta); // prepara arquivo para envia ao S3
			
			LOG.info("Finalizado upload:");
			
			return s3client.getUrl(bucketName, fileName).toURI();
		
		} catch (URISyntaxException e) {
			
			throw new RuntimeException("Erro ao converter URL para URI");
		}
		
	}
	
}
