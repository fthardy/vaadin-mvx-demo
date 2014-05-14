package de.vaadinbuch.mvxdemo;


/**
 * Abstrakte Basisklasse für alle Presenter.
 * 
 * @author Frank Hardy
 * 
 * @param <M>
 *            der Datentyp des Models.
 * @param <V>
 *            der Datentyp der View.
 */
public abstract class AbstractPresenter<M, V> {

	protected final M model;
	protected final V view;

	/**
	 * Initialisiert diese Presenterinstanz.
	 * 
	 * @param model
	 *            das Model des Presenters.
	 * @param view
	 *            die View des Presenters.
	 */
	public AbstractPresenter(M model, V view) {
		if (model == null) {
			throw new NullPointerException("Undefiniertes Model!");
		}
		if (view == null) {
			throw new NullPointerException("Undefinierte View!");
		}
		this.model = model;
		this.view = view;
	}
}
