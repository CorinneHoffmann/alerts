package com.sefetynet.alerts.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AgeCalculator {

	private Date birthDay;

	public int calculateAge(Date birthDay) {
		this.birthDay = birthDay;

		int age = 0;

		int days = 0;
		int months = 0;
		int years = 0;

		Calendar birthDate = Calendar.getInstance();
		birthDate.setTimeInMillis(birthDay.getTime());
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		years = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		months = now.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH);
		days = now.get(Calendar.DAY_OF_MONTH) - birthDate.get(Calendar.DAY_OF_MONTH);

		if (days >= 0 && months >= 0)
			age = years;

		if (days < 0 || months < 0)
			age = years - 1;

		return age;
	}

}
