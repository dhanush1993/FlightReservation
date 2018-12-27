package resources;

import java.net.MalformedURLException;
import java.net.URL;

public class Resources {
	
	public URL getResource(String name) throws MalformedURLException {
		URL url = new URL(getClass().getProtectionDomain().getCodeSource().getLocation().toString()+"resources/"+name);
		return url;
	}

}
