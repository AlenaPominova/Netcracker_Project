package Jackson;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

import main.Pojo;

public class MyModule extends SimpleModule {
	  private static final String NAME = "CustomModule";
	  private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

	  public MyModule() {
	    super(NAME, VERSION_UTIL.version());
	    addSerializer(Pojo.class, new MySerializer());
	  }
	}
