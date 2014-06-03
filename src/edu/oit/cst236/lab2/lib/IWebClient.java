package edu.oit.cst236.lab2.lib;

public interface IWebClient {

	String query(String url) throws InvalidRequestException,
			ConnectionException;

}