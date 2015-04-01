package com.talend.mdm.transaction.client;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PutMethod;

public class MDMTransactionClient {
	
	public static MDMTransaction newTransaction(String url, String username, String password) throws IOException {
		HttpClient client = new HttpClient();
		client.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
		
		PutMethod put = new PutMethod(url);
		put.setDoAuthentication(true);
		String tid = "";
		try {
			client.executeMethod(put);
			tid = put.getResponseBodyAsString();
		} catch (HttpException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			put.releaseConnection();
		}
		
		MDMTransaction result = new MDMTransaction();
		result.setUrl(url);
		result.setId(tid);
		result.setUsername(username);
		result.setPassword(password);
		
		return result;
	}
	
	public static String getMDMTransactionURL(String url, boolean isNewServer) {
		if(url == null || "".equals(url)) {
			return "";
		}
		
		int count = 3;
		int i=0;
		for(;i<url.length();i++) {
			char c = url.charAt(i);
			if('/' == c) {
				count--;
			}
			if(count == 0) {
				break;
			}
		}
		
		String result = url.substring(0, i);
		if(isNewServer){
			result += "/talendmdm/services/rest/transactions";
		} else {
			result += "/datamanager/services/transactions";
		}
		
		return result;
	}

	public static void main(String[] args) throws IOException {
		MDMTransaction mt = MDMTransactionClient.newTransaction("http://192.168.32.63:8080/datamanager/services/transactions", "administrator", "administrator");
		mt.commit();
		
		MDMTransaction mt1 = MDMTransactionClient.newTransaction("http://192.168.32.63:8080/talendmdm/services/rest/transactions", "administrator", "administrator");
		mt1.rollback();
		
		String url = "http://localhost:8080/talend/TalendPort";
		String mdmurl = MDMTransactionClient.getMDMTransactionURL(url, true);
		System.out.println(mdmurl);
	}
	
}
