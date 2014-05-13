package de.vaadinbuch.mvxdemo.login;

import de.vaadinbuch.mvxdemo.ViewAccessor;

/**
 * Definiert die öffentliche Schnittstelle der Anmeldekomponente.<br/>
 * Nutzer der Anmeldekomponente können über dieses Interface auf die Komponente
 * zugreifen.
 * 
 * @author Frank Hardy
 */
public interface LoginComponent extends ViewAccessor {

	/**
	 * Ein Callbackinterface, um auf eine erfolgreiche Anmeldung reagieren zu
	 * können.<br/>
	 * Objektinstanzen, die dieses Interface implementieren, können an der
	 * Anmeldekomponente mit der Methode
	 * {@link LoginComponent#addLoginSuccessHandler(LoginSuccessHandler)}
	 * registriert werden.
	 * 
	 * @author Frank Hardy
	 */
	interface LoginSuccessHandler {

		/**
		 * Wird aufgerufen, wenn ein Anmeldeversuch erfolgreich war.
		 * 
		 * @param userId
		 *            die Kennung des angemeldeten Benutzers.
		 */
		void onLoginSuccess(String userId);
	}

	/**
	 * Ein Callbackinterface, um auf einen fehlgeschlagenen Anmeldeversuch
	 * reagieren zu können.<br/>
	 * Objektinstanzen, die dieses Interface implementieren können an der
	 * Anmeldekomponente mit der Methode
	 * {@link LoginComponent#addLoginFailedHandler(LoginFailedHandler)}
	 * registriert werden.
	 * 
	 * @author Frank Hardy
	 */
	interface LoginFailedHandler {

		/**
		 * Wird aufgerufen, wenn ein Anmeldeversuch fehlgeschlagen ist.
		 */
		void onLoginFailed();
	}

	/**
	 * Registriert einen {@link LoginSuccessHandler}.<br/>
	 * Wenn ein Anmeldeversuch erfolgreich ist, werden alle an der
	 * Anmeldekomponente registrierten {@link LoginSuccessHandler} über einen
	 * Aufruf von {@link LoginSuccessHandler#onLoginSuccess(String)}
	 * entsprechend informiert.
	 * 
	 * @param handler
	 *            der zu registrierende Handler.
	 */
	void addLoginSuccessHandler(LoginSuccessHandler handler);

	/**
	 * Registriert einen {@link LoginFailedHandler}.<br/>
	 * Wenn ein Anmeldeversuch an der Anmeldekomponente nicht erfolgreich ist,
	 * werden alle an der Anmeldekomponente registrierten
	 * {@link LoginSuccessHandler} über einen Aufruf von
	 * {@link LoginSuccessHandler#onLoginFailed()} entsprechend informiert.
	 * 
	 * @param handler
	 *            der zu registrierende Handler.
	 */
	void addLoginFailedHandler(LoginFailedHandler handler);
}