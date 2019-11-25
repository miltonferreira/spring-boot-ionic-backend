package com.jotonferreira.cursomc.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	// classe que envia arquivos para o bucket da amazonS3
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class); // mostra no log mensagem de erros

	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName; // pega caminho do bucket
	
	// enviar um arquivo local para o bucket da amazon
	public void uploadFile(String localFilePath) {
		
		try {
			
			File file = new File(localFilePath);
			
			LOG.info("Iniciando upload:");
			
			s3client.putObject(new PutObjectRequest(bucketName, "Coringa.png", file)); // indica qual arquivo vai enviar
			
			LOG.info("Finalizado upload:");
		}
		catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException: " + e.getErrorMessage());
			LOG.info("Status Code: " + e.getErrorCode());
		}
		catch (AmazonClientException e) {
			LOG.info("AmazonClientException: " + e.getMessage());
		}
		
		
	}
	
}
