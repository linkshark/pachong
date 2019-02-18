package com.jt.pachong;


import java.util.Date;

public class Job {
	/**
	 * 表id
	 */
	private Integer id;
	/**
	 * 招聘信息标题
	 */
	private String title;//
	/**
	 * 公司名称
	 */
	private String company;//
	/**
	 * 最低月薪
	 */
	private Integer minSalary;//
	/**
	 * 最高月薪
	 */
	private Integer maxSalary;//
	/**
	 * 工作地点
	 */
	private String workLocation;//
	/**
	 * 学历
	 */
	private String education;//
	/**
	 * 福利待遇
	 */
	private String welfare;//
	/**
	 * 发布时间
	 */
	private Date created;//
	/**
	 * 信息链接
	 */
	private String url;//
	/**
	 * 职位描述
	 */
	private String info;//
	/**
	 * 工作经验
	 */
	private String experience;//
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(Integer minSalary) {
		this.minSalary = minSalary;
	}
	public Integer getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(Integer maxSalary) {
		this.maxSalary = maxSalary;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getWelfare() {
		return welfare;
	}
	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	@Override
	public String toString() {
		return "Job [id=" + id + ", title=" + title + ", company=" + company + ", minSalary=" + minSalary
				+ ", maxSalary=" + maxSalary + ", workLocation=" + workLocation + ", education=" + education
				+ ", welfare=" + welfare + ", created=" + created + ", url=" + url + ", info=" + info + ", experience="
				+ experience + "]";
	}
	
	
}
