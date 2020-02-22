package com.eas.modules.grade.entity;

import com.eas.modules.student.entity.Student;

import java.io.Serializable;
import java.util.List;

public class Grade implements Serializable {
	private static final long serialVersionUID = -2239736046742110784L;
	private Integer id;
	private String gname;
	private String academy;
	private String dept;
	private Integer gtotalnum;
	private List<Student> students;
	private boolean flag;

	public Grade() {
		super();
	}

	public Grade(Integer id, String gname, String academy, String dept, Integer gtotalnum) {
		super();
		this.id = id;
		this.gname = gname;
		this.academy = academy;
		this.dept = dept;
		this.gtotalnum = gtotalnum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getGtotalnum() {
		return gtotalnum;
	}

	public void setGtotalnum(Integer gtotalnum) {
		this.gtotalnum = gtotalnum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academy == null) ? 0 : academy.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((gname == null) ? 0 : gname.hashCode());
		result = prime * result + ((gtotalnum == null) ? 0 : gtotalnum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grade other = (Grade) obj;
		if (academy == null) {
			if (other.academy != null)
				return false;
		} else if (!academy.equals(other.academy))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (gname == null) {
			if (other.gname != null)
				return false;
		} else if (!gname.equals(other.gname))
			return false;
		if (gtotalnum == null) {
			if (other.gtotalnum != null)
				return false;
		} else if (!gtotalnum.equals(other.gtotalnum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grade [id=" + id + ", gname=" + gname + ", academy=" + academy + ", dept=" + dept + ", gtotalnum="
				+ gtotalnum + "]";
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
