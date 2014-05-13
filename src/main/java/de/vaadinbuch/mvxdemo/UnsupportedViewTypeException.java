package de.vaadinbuch.mvxdemo;

/**
 * Wird von {@link ViewAccessor#getViewAs(Class)} geworfen, wenn der geforderte
 * Viewtyp nicht unterstützt wird.
 * 
 * @author Frank Hardy
 */
public class UnsupportedViewTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedViewTypeException(String message) {
		super(message);
	}
}
