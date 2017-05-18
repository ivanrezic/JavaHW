package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	private List<ILocalizationListener> listeners = new ArrayList<>();

	public AbstractLocalizationProvider() {
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	public void fire(){
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}

	@Override
	public abstract String getString(String key);
}
