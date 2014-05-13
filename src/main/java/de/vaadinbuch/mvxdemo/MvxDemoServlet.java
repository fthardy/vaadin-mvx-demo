package de.vaadinbuch.mvxdemo;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

/**
 * Die Definition des Vaadin-Servlets mit der entsprechenden Servlet 3.0
 * Konfiguration.
 * 
 * @author Frank Hardy
 */
@SuppressWarnings("serial")
@WebServlet(//
value = "/*",//
asyncSupported = true,//
initParams = { @WebInitParam(name = "UIProvider", value = "de.vaadinbuch.mvxdemo.LoginAwareUIProvider") })
@VaadinServletConfiguration(//
productionMode = false, ui = LoginUI.class, widgetset = "de.vaadinbuch.mvxdemo.MvxDemoWidgetSet")
public class MvxDemoServlet extends VaadinServlet {
	// aktuell keine Implementierung
}