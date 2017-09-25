package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UiAutomatorHelper {

	// ���²�����Ҫ���ã�����id������id����׿id
	private static String android_id = "3";
	private static String jar_name = "";
	private static String test_class = "";
	private static String test_name = "";

	// �����ռ䲻��Ҫ���ã��Զ���ȡ�����ռ�Ŀ¼
	private static String workspace_path;

    public static void main(String[] args) {
		
	}
	public UiAutomatorHelper() {
		workspace_path = getWorkSpase();
		System.out.println("---�����ռ䣺\t\n" + getWorkSpase());
	}

	/**
	 * ����UI���̵��Թ�����������jar�����������������
	 * @param jarName
	 * @param testClass
	 * @param testName
	 * @param androidId
	 */
	public UiAutomatorHelper(String jarName, String testClass, String testName,
			String androidId) {
		System.out.println("-----------start--uiautomator--debug-------------");
		workspace_path = getWorkSpase();
		System.out.println("----�����ռ䣺\t\n" + getWorkSpase());

		jar_name = jarName;
		test_class = testClass;
		test_name = testName;
		android_id = androidId;
		runUiautomator();
		System.out.println("*******************");
		System.out.println("---FINISH DEBUG----");
		System.out.println("*******************");
	}
	/**
	 * ����build �� ����jar��ָ��Ŀ¼
	 * @param jarName
	 * @param testClass
	 * @param testName
	 * @param androidId
	 * @param isRun
	 */
	public UiAutomatorHelper(String jarName, String testClass, String testName,
			String androidId,String ctsTestCasePath){
		System.out.println("-----------start--uiautomator--debug-------------");
		workspace_path = getWorkSpase();
		System.out.println("----�����ռ䣺\t\n" + getWorkSpase());

		jar_name = jarName;
		test_class = testClass;
		test_name = testName;
		android_id = androidId;
		buildUiautomator(ctsTestCasePath);
		
		System.out.println("*******************");
		System.out.println("---FINISH DEBUG----");
		System.out.println("*******************");
		
	}
	// ���в���
	private void runUiautomator() {
		creatBuildXml();
		modfileBuild();
		buildWithAnt();
		if (System.getProperty("os.name").equals("Linux")) {
			pushTestJar(workspace_path + "/bin/" + jar_name + ".jar");
		}else{
		pushTestJar(workspace_path + "\\bin\\" + jar_name + ".jar");
		}
		
		if (test_name.equals("")) {
			runTest(jar_name, test_class);
			return;
		}
		runTest(jar_name, test_class + "#" + test_name);
	}		


	// 1--�ж��Ƿ���build
	public boolean isBuild() {
		File buildFile = new File("build.xml");
		if (buildFile.exists()) {
			return true;
		}
		// ����build.xml
		execCmd("cmd /c android create uitest-project -n " + jar_name + " -t "
				+ android_id + " -p " + workspace_path);
		return false;
	}

	// ����build.xml
	public void creatBuildXml() {
		execCmd("cmd /c android create uitest-project -n " + jar_name + " -t "
				+ android_id + " -p " + "\""+workspace_path+ "\"");
	}

	// 2---�޸�build
	public void modfileBuild() {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			File file = new File("build.xml");
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.matches(".*help.*")) {
						lineTxt = lineTxt.replaceAll("help", "build");
						// System.out.println("�޸ĺ� " + lineTxt);
					}
					stringBuffer = stringBuffer.append(lineTxt + "\t\n");
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}

		System.out.println("-----------------------");

		// �޸ĺ�д��ȥ
		writerText("build.xml", new String(stringBuffer));
		System.out.println("--------�޸�build���---------");
	}

	

	// 3---ant ִ��build
	public void buildWithAnt() {
		if (System.getProperty("os.name").equals("Linux")) {
			execCmd("ant");
			return;
		}
		execCmd("cmd /c ant");
	}

	// 4---push jar
	public void pushTestJar(String localPath) {
		localPath="\""+localPath+"\"";
		System.out.println("----jar��·���� "+localPath);
		String pushCmd = "adb push " + localPath + " /data/local/tmp/";
		System.out.println("----" + pushCmd);
		execCmd(pushCmd);
	}

	// ���в���
	public void runTest(String jarName, String testName) {
		String runCmd = "adb shell uiautomator runtest ";
		String testCmd = jarName + ".jar " + "--nohup -c " + testName;
		System.out.println("----runTest:  " + runCmd + testCmd);
		execCmd(runCmd + testCmd);
	}

	public String getWorkSpase() {
		File directory = new File("");
		String abPath = directory.getAbsolutePath();
		return abPath;
	}
	
	/**
	 * ����ִ��cmd����������Ϣ������̨
	 * @param cmd
	 */
	public void execCmd(String cmd) {
		System.out.println("----execCmd:  " + cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			//��ȷ�����
			InputStream input = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
                saveToFile(line, "runlog.log", false);
			}
			//���������
			InputStream errorInput = p.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(
					errorInput));
			String eline = "";
			while ((eline = errorReader.readLine()) != null) {
				System.out.println(eline);
                saveToFile(eline, "runlog.log", false);
			}       
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����д�����ݵ�ָ�����ļ���
	 * 
	 * @param path
	 *            �ļ���·��
	 * @param content
	 *            д���ļ�������
	 */
	public void writerText(String path, String content) {

		File dirFile = new File(path);

		if (!dirFile.exists()) {
			dirFile.mkdir();
		}

		try {
			// new FileWriter(path + "t.txt", true) �������true ���Բ�����ԭ��TXT�ļ����� ��д
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(path));
			bw1.write(content);
			bw1.flush();
			bw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void saveToFile(String text,String path,boolean isClose) {
    	File file=new File("runlog.log");   	
		BufferedWriter bf=null;
		try {
		    FileOutputStream outputStream=new FileOutputStream(file,true);
		    OutputStreamWriter outWriter=new OutputStreamWriter(outputStream);
		    bf=new BufferedWriter(outWriter);
			bf.append(text);
			bf.newLine();
			bf.flush();
			
			if(isClose){
				bf.close();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
    /**
     * ���󣺱���͸���jar��ָ���ļ�
     * @param newPath
     */
    private void buildUiautomator(String newPath) {
		creatBuildXml();
		modfileBuild();
		buildWithAnt();
		//�����ļ���ָ���ļ���
		copyFile(workspace_path + "\\bin\\" + jar_name + ".jar", newPath);
		
	}
    /** 
     * ���Ƶ����ļ� 
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt 
     * @param newPath String ���ƺ�·�� �磺f:/fqf.txt 
     * @return boolean 
     */ 
   public void copyFile(String oldPath, String newPath) { 
	   System.out.println("Դ�ļ�·����"+oldPath);
	   System.out.println("Ŀ���ļ�·����"+newPath);
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //�ļ�����ʱ 
               InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //�ֽ��� �ļ���С 
                   System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("���Ƶ����ļ���������"); 
           e.printStackTrace(); 

       } 

   } 

	

}
