package org.spgroup.dao;

import java.util.List;

public interface FriendDao {

	User addUser(User u1);

	User findUserByEmail(String email);

	void addConnection(User u1, User u2);

	void subscribe(User requestor, User target);

	void block(User requestor, User target);

	List<String> getConnections(User u);

	List<String> getRecipients(String email);
}
