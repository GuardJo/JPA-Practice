package com.practice.jpa.chapter09.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CH09_PERIOD")
public class Period9 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date finishedDate;

	protected Period9() {

	}

	private Period9(Date startDate, Date finishedDate) {
		this.startDate = startDate;
		this.finishedDate = finishedDate;
	}

	public static Period9 create() {
		return new Period9(new Date(), new Date());
	}

	public Long getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	@Override
	public String toString() {
		return "Period9{" +
			"id=" + id +
			", startDate=" + startDate +
			", finishedDate=" + finishedDate +
			'}';
	}
}
