package com.tom.javaspring.dao.impl;

import com.tom.javaspring.dao.RoleDAO;
import com.tom.javaspring.dto.RoleParams;
import com.tom.javaspring.entity.Customer;
import com.tom.javaspring.entity.Role;
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
public class RoleDAOImpl implements RoleDAO {
    private final SessionFactory sessionFactory;

    public RoleDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Role> getRoles(RoleParams roleParams) {
        String search = roleParams.getSearch();
        String sort = roleParams.getSort();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Role> query = builder.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);

        // Tạo danh sách Predicate để thêm điều kiện
        List<Predicate> predicates = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            Predicate name = builder.like(
                    builder.lower(root.get("name")), "%" + search.toLowerCase() + "%"
            );

            predicates.add(builder.or(name));
        }

        // Thêm tất cả các điều kiện vào CriteriaQuery
        query.where(predicates.toArray(new Predicate[0]));

        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("nameDesc")) {
                query.orderBy(builder.asc(root.get("name")));
            } else {
                query.orderBy(builder.asc(root.get("id")));
            }
        }

        int page = roleParams.getPage();
        int pageSize = roleParams.getPageSize();
        int firstResult = (page - 1) * pageSize;

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public List<Role> getRoles() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Role> theQuery = currentSession.createQuery("from Role", Role.class);

        return theQuery.getResultList();
    }

    @Override
    public int getRoleCount(RoleParams roleParams) {
        String search = roleParams.getSearch();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Role> root = query.from(Role.class);

        query.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            Predicate name = builder.like(
                    builder.lower(root.get("name")), "%" + search.toLowerCase() + "%"
            );


            predicates.add(builder.or(name));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return Math.toIntExact(sessionFactory.getCurrentSession()
                .createQuery(query)
                .getSingleResult());
    }

    @Override
    public void saveRole(Role role) {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(role);
    }

    @Override
    public Role getById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Role> theQuery = currentSession.createQuery("from Role where id = :id", Role.class);
        theQuery.setParameter("id", id);

        return theQuery.getSingleResult();
    }

    @Override
    public void deleteRole(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Role role = getById(id);
        currentSession.delete(role);
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Role> theQuery = currentSession.createQuery("from Role where name = :name");
        theQuery.setParameter("name", name);
        return  theQuery.getSingleResult();
    }
}
