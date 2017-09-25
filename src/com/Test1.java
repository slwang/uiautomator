package com;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Test1 extends UiAutomatorTestCase {

	public static void main(String[] args) {
		String jarName ="uiautomatertest";
		String testClass ="com.Test1";
		String testName ="testOFO";
		String androidId ="1";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);

	}
	
	public void testHome(){
		UiDevice.getInstance().pressHome();
	}
	
	public void testMenu(){
		UiDevice.getInstance().pressMenu();
	}
	
	public void testRecentApps() throws RemoteException{
		UiDevice.getInstance().pressRecentApps();
	}
	
	public void testOFO() throws RemoteException, UiObjectNotFoundException, InterruptedException{
		UiDevice.getInstance().pressHome();
		UiObject ofo = new UiObject(new UiSelector().text("ofo共享单车"));
		ofo.clickAndWaitForNewWindow();
		
		
		UiObject login = new UiObject(new UiSelector().resourceId("labofo:id/tv_login_or_register"));
		login.click();
		
		Thread.sleep(1000);
		
		UiObject phone = new UiObject(new UiSelector().resourceId("so.ofo.labofo:id/phone"));
		phone.clearTextField();
		phone.setText("13000000001");
	}
}
