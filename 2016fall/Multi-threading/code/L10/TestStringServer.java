package src;

import java.rmi.RemoteException;

public class TestStringServer implements TestString {
	
	private String testString;
	
	TestStringServer (String testString) {
		this.testString = testString;
	}

	@Override
	public String getTestString() throws RemoteException {
		return testString;
	}

}
