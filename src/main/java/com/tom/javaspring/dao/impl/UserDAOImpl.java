package com.tom.javaspring.dao.impl;

import com.tom.javaspring.constant.UserSortColumn;
import com.tom.javaspring.dao.UserDAO;
import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public UserEntity findByUserName(String userName) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where userName = :userName", UserEntity.class)
                .setParameter("userName", userName)
                .uniqueResult();
    }

    @Override
    @Transactional
    public UserEntity findByEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where email = :email", UserEntity.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public List<UserEntity> getUsers(UserParams userParams) {
        String search = userParams.getSearch();
        String sort = userParams.getSort();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        if (search != null && !search.trim().isEmpty()) {
            query.where(builder.or(
                            builder.like(builder.lower(root.get("userName")), "%" + search.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("email")), "%" + search.toLowerCase() + "%")
                    )
            );
        }

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case UserSortColumn.User_Name:
                    query.orderBy(builder.asc(root.get("userName")));
                    break;
                case UserSortColumn.Email:
                    query.orderBy(builder.asc(root.get("email")));
                    break;
                default:
                    query.orderBy(builder.asc(root.get("id")));
                    break;
            }
        }

        int page = userParams.getPage();
        int pageSize = userParams.getPageSize();
        int firstResult = (page - 1) * pageSize;

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public int getUserCount(UserParams userParams) {
        String search = userParams.getSearch();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        query.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            Predicate userNamePredicate = builder.like(
                    builder.lower(root.get("userName")), "%" + search.toLowerCase() + "%"
            );
            Predicate emailPredicate = builder.like(
                    builder.lower(root.get("email")), "%" + search.toLowerCase() + "%"
            );

            predicates.add(builder.or(userNamePredicate, emailPredicate));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return Math.toIntExact(sessionFactory.getCurrentSession()
                .createQuery(query)
                .getSingleResult());
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(userEntity);
    }

    @Override
    public UserEntity getById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<UserEntity> theQuery = currentSession.createQuery("from UserEntity where id = :id", UserEntity.class);
        theQuery.setParameter("id", id);

        return theQuery.getSingleResult();
    }

    @Override
    public void deleteUser(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<UserEntity> theQuery = currentSession.createQuery("delete from UserEntity where id = :id");
        theQuery.setParameter("id", id);
        theQuery.executeUpdate();
    }
}
