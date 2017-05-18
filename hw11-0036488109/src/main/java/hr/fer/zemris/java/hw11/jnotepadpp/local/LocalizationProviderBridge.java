package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationListener listener = () -> fire();
	private ILocalizationProvider parent;
	
	public LocalizationProviderBridge(ILocalizationProvider provider){
		this.parent = provider;
	}
	
	public void connect(){
		if (connected) {
			return;
		}
		connected = true;
		parent.addLocalizationListener(listener);
	}
	
	public void disconnect(){
		if (!connected) {
			return;
		}
		connected = false;
		parent.removeLocalizationListener(listener);
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
