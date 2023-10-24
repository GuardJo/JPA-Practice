package com.practice.jpa.chapter09.domain.types;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Period {
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date finishedDate;

	protected Period() {

	}

	private Period(Date startDate, Date finishedDate) {
		this.startDate = startDate;
		this.finishedDate = finishedDate;
	}

	public static Period create() {
		return new Period(new Date(), new Date());
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	@Override
	public String toString() {
		return "Period{" +
			"startDate=" + startDate +
			", finishedDate=" + finishedDate +
			'}';
	}
}
