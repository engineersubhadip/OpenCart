package threadSafe;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PropertyManagement {

	private static PropertyManagement instance;
	private static Lock lock = new ReentrantLock();
	private static ThreadLocal<Properties> tLocalProp = new ThreadLocal<>();

	private PropertyManagement() {
	};

	public static PropertyManagement getInstance() {
		if (instance == null) {
			lock.lock();
			if (instance == null) {
				instance = new PropertyManagement();
			}
			lock.unlock();
		}
		return instance;
	}

	public void setProperty(String filePath) throws IOException { // this method will be invoked by a thread via the
																	// shared object
		if (tLocalProp.get() == null) {
			tLocalProp.set(new Properties());
			try (FileInputStream file = new FileInputStream(filePath)) {
				tLocalProp.get().load(file); // once the loading of data from input stream to property object is done,
												// the fileinputstream object automatically closes.
			}
		}
	}

	public Properties getProperty() { // this method will be executed by a thread, via the shared object
		if (tLocalProp.get() != null) {
			return tLocalProp.get();
		}
		return null;
	}

	public void removeProperty() { // this method will be executed by a thread, via the shared object
		if (tLocalProp.get() != null) {
			tLocalProp.remove();
		}
	}
}
