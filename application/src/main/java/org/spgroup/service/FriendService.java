package org.spgroup.service;

import java.util.List;

public interface FriendService {

	boolean addFriends(List<String> users);

	List<String> getFriends(String user);

	List<String> getCommonFriends(List<String> users);

	boolean subscribe(String requestor, String target);

	boolean block(String requestor, String target);

	List<String> getRecipients(String sender, String text);
}
