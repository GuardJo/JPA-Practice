package com.practice.jpa.chapter10.dto;

public class TeamAvgAgeDto {
	private String teamName;
	private double teamAvgAge;

	public TeamAvgAgeDto(String teamName, double teamAvgAge) {
		this.teamName = teamName;
		this.teamAvgAge = teamAvgAge;
	}

	public String getTeamName() {
		return teamName;
	}

	public double getTeamAvgAge() {
		return teamAvgAge;
	}

	@Override
	public String toString() {
		return "TeamAvgAgeDto{" +
			"teamName='" + teamName + '\'' +
			", teamAvgAge=" + teamAvgAge +
			'}';
	}
}
