package com;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Test  extends UiAutomatorTestCase {
	public void testDemo() throws UiObjectNotFoundException {  
		//switchLanguage("zh");
		//switchLanguage("en");
    }  
	
	private void  switchLanguage(String language) throws UiObjectNotFoundException{
		
			getUiDevice().pressHome();  //获取UiDevice实例，模拟按压主屏幕键  
			 UiScrollable appItems = new UiScrollable( new UiSelector().scrollable(true));   
			 appItems.setAsHorizontalList();

				
	        // 进入设置菜单    
			if ("zh".compareToIgnoreCase(language)==0){
				UiObject settingApp = appItems.getChildByText(new UiSelector().text("Settings"), "Settings", true);  
	        settingApp.click();    
			}
			
			if ("en".compareToIgnoreCase(language)==0){
				UiObject settingApp = appItems.getChildByText(new UiSelector().text("设置"), "设置", true);  
		        settingApp.click();    
				}
	       /*//休眠3秒    
	        try {    
	            Thread.sleep(3000);    
	        } catch (InterruptedException e1) {    
	            // TODO Auto-generated catch block    
	            e1.printStackTrace();    
	        }    */
	        // 进入语言和输入法设置    
			
	        UiScrollable settingItems = new UiScrollable( new UiSelector().scrollable(true));    
	    
	      
	        if ("zh".compareToIgnoreCase(language)==0){
	        	UiObject languageAndInputItem = settingItems.getChildByText(new UiSelector().text("More settings"), "More settings", true);  
	        	languageAndInputItem.clickAndWaitForNewWindow();  

	        	/*   UiObject li=new UiObject(new UiSelector().text("Language and input"));  
	        li.clickAndWaitForNewWindow();  */

	        	// 进入语言和输入法设置    
	        	UiScrollable settingItems2 = new UiScrollable( new UiSelector().scrollable(true));    

	        	UiObject Lang=settingItems2.getChildByText(new UiSelector().text("Language"), "Language", true);   
	        	Lang.clickAndWaitForNewWindow();  

	        	UiObject L=new UiObject(new UiSelector().text("简体中文"));  
	        	L.click();  
	        }
	        
	        if ("en".compareToIgnoreCase(language)==0){
	        	UiObject languageAndInputItem = settingItems.getChildByText(new UiSelector().text("更多设置"), "更多设置", true);  
	        	languageAndInputItem.clickAndWaitForNewWindow();  

	        	/*   UiObject li=new UiObject(new UiSelector().text("Language and input"));  
	        li.clickAndWaitForNewWindow();  */

	        	// 进入语言和输入法设置    
	        	UiScrollable settingItems2 = new UiScrollable( new UiSelector().scrollable(true));    

	        	UiObject Lang=settingItems2.getChildByText(new UiSelector().text("语言"), "语言", true);   
	        	Lang.clickAndWaitForNewWindow();  

	        	UiObject L=new UiObject(new UiSelector().text("English"));  
	        	L.click();  
	        }
	          
	        getUiDevice().pressBack();  
	        getUiDevice().pressBack();  
	        getUiDevice().pressBack();  
	}
}
