package org.spgroup.dao;

import java.util.UUID;

import lombok.Data;

@Data
public class Relationship {

	private String id;
	private String friend_id;
	private String friend_type;
	private int block;
}
