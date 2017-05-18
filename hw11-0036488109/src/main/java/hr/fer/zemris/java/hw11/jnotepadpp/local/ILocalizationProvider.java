package hr.fer.zemris.java.hw11.jnotepadpp.local;

public interface ILocalizationProvider {

	public void addLocalizationListener(ILocalizationListener listener);
	public void removeLocalizationListener(ILocalizationListener listener);
	public String getString(String key);
	
}
