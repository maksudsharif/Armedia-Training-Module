package com.someco.submission;

import java.util.List;

public interface FDAService
{
	List<String> getOffices(String sitename);
	List<String> getDivisions(String sitename, String office);
	List<String> getBranches(String sitename, String office, String division);
}
