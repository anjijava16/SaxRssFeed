package com.iwinnerapps.xml.service;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
public static List<EmployeeInfoForm> insertEmployeeInfo(EmployeeInfoForm eForm){
	List<EmployeeInfoForm> lInfo=new ArrayList<EmployeeInfoForm>();
	
	System.out.println("T"+eForm.toString());
	return lInfo;
}
}
