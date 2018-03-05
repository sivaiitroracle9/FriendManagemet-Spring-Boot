package org.spgroup.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.spgroup.common.EntityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FriendDaoImpl implements FriendDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public User addUser(User user) {

		UUID uuid = UUID.randomUUID();
		user.setId(uuid.toString());
		jdbcTemplate.update("INSERT INTO USER (ID, EMAIL) VALUES(?, ?)",
				user.getId(), user.getEmail());
		return user;
	}

	@Override
	public User findUserByEmail(String email) {
		return jdbcTemplate.query("SELECT ID, EMAIL FROM USER WHERE EMAIL='"
				+ email + "' LIMIT 1", new UserRsExtractor());
	}

	@Override
	public void addConnection(User u1, User u2) {
		User user1 = findUserByEmail(u1.getEmail());
		User user2 = findUserByEmail(u2.getEmail());
		if (user1 == null || user1.getId() == null) {
			user1 = addUser(u1);
		}

		if (user2 == null || user2.getId() == null) {
			user2 = addUser(u2);
		}
		Relationship relation = jdbcTemplate.query(
				"SELECT * FROM RELATIONSHIP WHERE (ID = ? AND FRIEND_ID = ?)",
				new Object[] { user1.getId(), user2.getId() }, new int[] {
						Types.VARCHAR, Types.VARCHAR },
				new RelationshipRsetExtractor());
		if (relation == null) {
			jdbcTemplate.update(
					"INSERT INTO RELATIONSHIP (ID, FRIEND_ID) VALUES (?, ?)",
					new Object[] { user1.getId(), user2.getId() }, new int[] {
							Types.VARCHAR, Types.VARCHAR });
		} else {
			jdbcTemplate
					.update("UPDATE RELATIONSHIP SET FRIEND_TYPE = ? WHERE ID =? AND FRIEND_ID=?",
							new Object[] { "FRIEND", user1.getId(),
									user2.getId() }, new int[] { Types.VARCHAR,
									Types.VARCHAR, Types.VARCHAR });
		}

		relation = jdbcTemplate.query(
				"SELECT * FROM RELATIONSHIP WHERE (ID = ? AND FRIEND_ID = ?)",
				new Object[] { user2.getId(), user1.getId() }, new int[] {
						Types.VARCHAR, Types.VARCHAR },
				new RelationshipRsetExtractor());
		if (relation == null) {
			jdbcTemplate.update(
					"INSERT INTO RELATIONSHIP (ID, FRIEND_ID) VALUES (?, ?)",
					new Object[] { user2.getId(), user1.getId() }, new int[] {
							Types.VARCHAR, Types.VARCHAR });
		} else {
			jdbcTemplate
					.update("UPDATE RELATIONSHIP SET FRIEND_TYPE = ? WHERE ID =? AND FRIEND_ID=?",
							new Object[] { "FRIEND", user2.getId(),
									user1.getId() }, new int[] { Types.VARCHAR,
									Types.VARCHAR, Types.VARCHAR });
		}

	}

	@Override
	public void subscribe(User requestor, User target) {

		Relationship relation = jdbcTemplate.query(
				"SELECT * FROM RELATIONSHIP WHERE (ID = ? AND FRIEND_ID = ?)",
				new Object[] { target.getId(), requestor.getId() }, new int[] {
						Types.VARCHAR, Types.VARCHAR },
				new RelationshipRsetExtractor());
		if (relation == null) {
			jdbcTemplate
					.update("INSERT INTO RELATIONSHIP (ID, FRIEND_ID, FRIEND_TYPE) VALUES(?, ?, ?)",
							new Object[] { target.getId(), requestor.getId(),
									"SUBSCRIBE" }, new int[] { Types.VARCHAR,
									Types.VARCHAR, Types.VARCHAR });
		}
	}

	@Override
	public void block(User requestor, User target) {
		Relationship relation = jdbcTemplate.query(
				"SELECT * FROM RELATIONSHIP WHERE (ID = ? AND FRIEND_ID = ?)",
				new Object[] { target.getId(), requestor.getId() }, new int[] {
						Types.VARCHAR, Types.VARCHAR },
				new RelationshipRsetExtractor());
		if (relation == null) {
			jdbcTemplate
					.update("INSERT INTO RELATIONSHIP (ID, FRIEND_ID, FRIEND_TYPE, BLOCK) VALUES(?, ?, ?, ?)",
							new Object[] { target.getId(), requestor.getId(),
									"SUBSCRIBE", 1 }, new int[] {
									Types.VARCHAR, Types.VARCHAR,
									Types.VARCHAR, Types.INTEGER });
		} else {
			jdbcTemplate
					.update("UPDATE RELATIONSHIP SET BLOCK=? WHERE ID=? AND FRIEND_ID=?",
							new Object[] { 1, target.getId(), requestor.getId() },
							new int[] { Types.INTEGER, Types.VARCHAR,
									Types.VARCHAR });
		}

	}

	@Override
	public List<String> getConnections(User u) {
		if (u == null)
			return new ArrayList<String>();
		return jdbcTemplate
				.queryForList(
						"SELECT DISTINCT U.EMAIL FROM RELATIONSHIP R, USER U "
								+ "WHERE R.FRIEND_ID=U.ID AND R.ID=? AND R.FRIEND_TYPE='FRIEND'",
						new Object[] { u.getId() },
						new int[] { Types.VARCHAR }, String.class);
	}

	@Override
	public List<String> getRecipients(String email) {
		User user = findUserByEmail(email);
		return jdbcTemplate.queryForList(
				"SELECT DISTINCT U.EMAIL FROM RELATIONSHIP R, USER U "
						+ "WHERE R.FRIEND_ID=U.ID AND R.ID=? AND R.BLOCK=0",
				new Object[] { user.getId() }, new int[] { Types.VARCHAR },
				String.class);
	}

	private class UserRsExtractor implements ResultSetExtractor<User> {

		@Override
		public User extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			if (rs.next()) {
				User user = new User(rs.getString(EntityConstants.email));
				user.setId(rs.getString(EntityConstants.id));
				return user;
			}
			return null;
		}
	}

	private class RelationshipRsetExtractor implements
			ResultSetExtractor<Relationship> {
		@Override
		public Relationship extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			if (rs.next()) {
				Relationship relation = new Relationship();
				relation.setId(rs.getString(EntityConstants.id));
				relation.setFriend_id(rs.getString(EntityConstants.friend_id));
				relation.setBlock(rs.getInt(EntityConstants.block));
				relation.setFriend_type(rs
						.getString(EntityConstants.friend_type));
				return relation;
			}
			return null;
		}
	}

}
