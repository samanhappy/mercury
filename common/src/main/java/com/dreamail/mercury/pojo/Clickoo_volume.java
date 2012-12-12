package com.dreamail.mercury.pojo;

public class Clickoo_volume {

	private long id;
	
	private String name;
	
	private String type;
	
	private long file_bits;
	
	private String path;
	
	private long space;

	public Clickoo_volume(){}
	
	public Clickoo_volume(long id, String name, String type, long file_bits,
			String path, long space) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.file_bits = file_bits;
		this.path = path;
		this.space = space;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getFile_bits() {
		return file_bits;
	}

	public void setFile_bits(long fileBits) {
		file_bits = fileBits;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSpace() {
		return space;
	}

	public void setSpace(long space) {
		this.space = space;
	}

	
}
