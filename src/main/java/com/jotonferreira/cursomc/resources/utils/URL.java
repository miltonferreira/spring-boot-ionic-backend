package com.jotonferreira.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	// decodifica caso na busca coloque espaço como "TV LED" = "TV%20LED" no navegador
	public static String DecodeParam(String s) {
		
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		
	}
	
	public static List<Integer> decodeInList(String s){
		
		// forma classica
//		String[] vet = s.split(",");
//		
//		List<Integer> list = new ArrayList<Integer>();
//		
//		for(int i=0; i<vet.length; i++) {
//			list.add(Integer.parseInt(vet[i]));
//		}
//		
//		return list;
		
		// forma com lambda
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
}
