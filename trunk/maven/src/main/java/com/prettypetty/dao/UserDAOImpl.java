package com.prettypetty.dao;


import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.prettypetty.bean.User;
import com.prettypetty.dao.exceptions.NotExistException;

public class UserDAOImpl extends HibernateDaoSupport implements UserDAO{
	/**
	 * Save or Update user
	 */
	public void saveUser(User user) {
		this.getHibernateTemplate().saveOrUpdate(user);

	}

	/**
	 * delete user
	 */
	public void deleteUser(User user) {
		this.getHibernateTemplate().delete(user);
	}

	/**
	 * find user by name
	 */
	public User findUserByName(String username) throws NotExistException {
		try {
			User r = (User)this.getHibernateTemplate().find("from User u where u.username =?", username).get(0);			
			return r;
		} catch (IndexOutOfBoundsException e) {
			throw new NotExistException();
		} catch (Exception ex) {
			throw new NotExistException();
		} finally {
		}		
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllusers() {
		return this.getHibernateTemplate().loadAll(User.class);
	}
}
