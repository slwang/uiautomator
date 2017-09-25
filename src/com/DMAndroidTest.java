package com;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class DMAndroidTest extends UiAutomatorTestCase {
	public void testDemo() throws UiObjectNotFoundException {  
		// 模拟 HOME 键点击事件
		getUiDevice().pressHome();


		UiScrollable settingItems = new UiScrollable( new UiSelector().scrollable(true));   
		settingItems.setAsHorizontalList();
		settingItems.flingToEnd(3);//只滑动两次快速滚动到结尾

		UiObject dmAppsButton = settingItems.getChildByText(new UiSelector().text("豆蔓智投"), "豆蔓智投", true);
		//UiObject dmAppsButton = settingItems.getChildByText(new UiSelector().text("Weibo"), "Weibo", true);  
		assertTrue("Not Exists douman", dmAppsButton.exists());
		dmAppsButton.clickAndWaitForNewWindow();  




	}  

}
