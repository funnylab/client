package org.springframework.security.oauth.examples.tonr.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth.examples.tonr.SampleException;
import org.springframework.security.oauth.examples.tonr.SampleService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestOperations;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Ryan Heaton
 */
public class SampleServiceImpl implements SampleService {

	private String samplePhotoListURL;
	private String sampleTrustedMessageURL;
	private String samplePhotoURLPattern;
	private RestOperations sampleRestTemplate;
	private RestOperations trustedClientRestTemplate;

	public List<String> getSamplePhotoIds() throws SampleException {
		try {
			
			
			InputStream photosXML = new ByteArrayInputStream(sampleRestTemplate.getForObject(
					URI.create(samplePhotoListURL), byte[].class));

			final List<String> photoIds = new ArrayList<String>();
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setValidating(false);
			parserFactory.setXIncludeAware(false);
			parserFactory.setNamespaceAware(false);
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(photosXML, new DefaultHandler() {
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					if ("photo".equals(qName)) {
						photoIds.add(attributes.getValue("id"));
					}
				}
			});
			return photoIds;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (SAXException e) {
			throw new IllegalStateException(e);
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	public InputStream loadSamplePhoto(String id) throws SampleException {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa : " + URI.create(samplePhotoListURL));
		
		OAuth2RestTemplate tt = (OAuth2RestTemplate)sampleRestTemplate;
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa : " + tt.toString());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getValue : " + tt.getAccessToken().getValue());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getExpiresIn : " + tt.getAccessToken().getExpiresIn());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getExpiration : " + tt.getAccessToken().getExpiration());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getRefreshToken : " + tt.getAccessToken().getRefreshToken());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getTokenType : " + tt.getAccessToken().getTokenType());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getRequestFactory : " + tt.getRequestFactory().toString());
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa getRequestFactory1 : " + tt.getOAuth2ClientContext().getAccessToken().getValue());
		
		return new ByteArrayInputStream(sampleRestTemplate.getForObject(
				URI.create(String.format(samplePhotoURLPattern, id)), byte[].class));
	}

	public String getTrustedMessage() {
		return this.trustedClientRestTemplate.getForObject(URI.create(sampleTrustedMessageURL), String.class);
	}

	public void setSamplePhotoURLPattern(String samplePhotoURLPattern) {
		this.samplePhotoURLPattern = samplePhotoURLPattern;
	}

	public void setSamplePhotoListURL(String samplePhotoListURL) {
		this.samplePhotoListURL = samplePhotoListURL;
	}
	
	public void setSampleTrustedMessageURL(String sampleTrustedMessageURL) {
		this.sampleTrustedMessageURL = sampleTrustedMessageURL;
	}

	public void setSampleRestTemplate(OAuth2RestTemplate sampleRestTemplate) {
		this.sampleRestTemplate = sampleRestTemplate;
	}

	public void setTrustedClientRestTemplate(RestOperations trustedClientRestTemplate) {
		this.trustedClientRestTemplate = trustedClientRestTemplate;
	}

}
