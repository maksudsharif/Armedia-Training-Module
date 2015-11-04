package com.someco.cmis.examples;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

public class FoundationTest extends CMISExampleBase
{
	public static void main(String[] argv) {
		FoundationTest ft = new FoundationTest();
		ft.setUser("admin");
		ft.setPassword("admin");
		Session session = ft.getSession();
		
		Folder folder = (Folder) session.getObjectByPath("/Sites/swsdp/documentLibrary");
		
		System.out.println(folder.toString());
	}
}
