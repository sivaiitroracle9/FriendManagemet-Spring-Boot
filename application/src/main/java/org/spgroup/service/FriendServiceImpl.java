package org.spgroup.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.spgroup.dao.FriendDao;
import org.spgroup.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class FriendServiceImpl implements FriendService {

	@Autowired
	FriendDao dao;
	
	@Override
	public boolean addFriends(List<String> users) {
		try{
			dao.addConnection(new User(users.get(0)), new User(users.get(1)));
			return true;
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> getFriends(String user) {
		User userByEmail = dao.findUserByEmail(user);
		return dao.getConnections(userByEmail);
	}

	@Override
	public List<String> getCommonFriends(List<String> users) {
		Set<String> f1 = new HashSet<String>(getFriends(users.get(0)));
		Set<String> f2 = new HashSet<String>(getFriends(users.get(1)));
		return f1.stream().filter(f -> f2.contains(f)).collect(Collectors.toList());
	}

	@Override
	public boolean subscribe(String requestor, String target) {
		try {
			User user1 = dao.findUserByEmail(requestor);
			User user2 = dao.findUserByEmail(target);
			if (user1 == null || user1.getId() == null) {
				user1 = dao.addUser(new User(requestor));
			}

			if (user2 == null || user2.getId() == null) {
				user2 = dao.addUser(new User(target));
			}
			dao.subscribe(user1, user2);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean block(String requestor, String target) {
		try {
			User user1 = dao.findUserByEmail(requestor);
			User user2 = dao.findUserByEmail(target);
			if (user1 == null || user1.getId() == null) {
				user1 = dao.addUser(new User(requestor));
			}

			if (user2 == null || user2.getId() == null) {
				user2 = dao.addUser(new User(target));
			}
			dao.block(user1, user2);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> getRecipients(String sender, String text) {
		return dao.getRecipients(sender);
	}

}
