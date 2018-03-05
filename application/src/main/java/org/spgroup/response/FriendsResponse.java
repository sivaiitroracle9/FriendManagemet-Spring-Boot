package org.spgroup.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.spgroup.common.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendsResponse implements Response {

	private boolean success;
	private List<String> friends;
	private int count;
	
	public FriendsResponse(List<String> friends) {
		if(friends == null) {
			success = false;
			this.friends = new ArrayList<String>();
		} else {
			success = true;
			this.friends = new ArrayList<String>(friends);
		}
		count = this.friends.size();
	}
}
