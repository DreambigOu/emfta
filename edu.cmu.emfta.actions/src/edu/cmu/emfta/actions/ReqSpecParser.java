package edu.cmu.emfta.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReqSpecParser {

	public List<SystemRequirements> parse(String file) {
		List<SystemRequirements> list = new ArrayList<SystemRequirements>();
		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			String str = sc.nextLine();
			String arr[] = str.split(" ");

			if (arr[0] == "description") {

			}

		}
		return list;
	}

}

class SystemRequirements {
	String name;
	List<Requirements> requirementsList;

}

class Requirements {
	String name; // name
	String reqOrder; // requirement number
	String description;
	String val;
	String compute;
	String value;
	String seegoal;

}