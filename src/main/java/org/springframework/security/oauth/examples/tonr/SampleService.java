package org.springframework.security.oauth.examples.tonr;

import java.io.InputStream;
import java.util.List;

/**
 * @author Ryan Heaton
 */
public interface SampleService {

	/**
	 * Get the list of sample photo ids for the current user.
	 * 
	 * @return The list of photo ids for the current user.
	 */
	List<String> getSamplePhotoIds() throws SampleException;

	/**
	 * Loads the sample photo for the current user.
	 * 
	 * @param id the id or the photo.
	 * @return The sample photo.
	 */
	InputStream loadSamplePhoto(String id) throws SampleException;

	/**
	 * @return a message
	 */
	String getTrustedMessage();

}
