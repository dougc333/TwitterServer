package com.example;

public class Tweets {
	private String id;
    private String created_at;
    private String text;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

    public String toString(){
    	return  "id:"+id+" created at:"+created_at+ " tweet:"+text;
    }

}
