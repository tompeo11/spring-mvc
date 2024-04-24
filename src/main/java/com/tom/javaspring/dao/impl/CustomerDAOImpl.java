package com.tom.javaspring.dao.impl;

import com.tom.javaspring.constant.CustomerSortColumn;
import com.tom.javaspring.dao.CustomerDAO;
import com.tom.javaspring.dto.CustomerParams;
import com.tom.javaspring.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
    private final SessionFactory sessionFactory;

    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Customer> getCustomers(CustomerParams customerParams) {
        String search = customerParams.getSearch();
        String sort = customerParams.getSort();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);

        if (search != null && !search.trim().isEmpty()) {
            query.where(builder.or(
                    builder.like(builder.lower(root.get("firstName")), "%" + search.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%")
                )
            );
        }

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case CustomerSortColumn.FIRST_NAME:
                    query.orderBy(builder.asc(root.get("firstName")));
                    break;
                case CustomerSortColumn.LAST_NAME:
                    query.orderBy(builder.asc(root.get("lastName")));
                    break;
                case CustomerSortColumn.EMAIL:
                    query.orderBy(builder.asc(root.get("email")));
                    break;
                default:
                    query.orderBy(builder.asc(root.get("id")));
                    break;
            }
        }

        int page = customerParams.getPage();
        int pageSize = customerParams.getPageSize();
        int firstResult = (page - 1) * pageSize;

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public int getCustomerCount(CustomerParams customerParams) {
        String search = customerParams.getSearch();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Customer> root = query.from(Customer.class);

        query.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            Predicate firstNamePredicate = builder.like(
                    builder.lower(root.get("firstName")), "%" + search.toLowerCase() + "%"
            );
            Predicate lastNamePredicate = builder.like(
                    builder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%"
            );

            predicates.add(builder.or(firstNamePredicate, lastNamePredicate));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return Math.toIntExact(sessionFactory.getCurrentSession()
                .createQuery(query)
                .getSingleResult());
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Customer> theQuery = currentSession.createQuery("from Customer where id = :id", Customer.class);
        theQuery.setParameter("id", id);

        return theQuery.getSingleResult();
    }

    @Override
    public List<Customer> searchCustomer(String theSearchName) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Customer> theQuery = null;

        if (theSearchName != null && !theSearchName.trim().isEmpty()) {
            theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", theSearchName);
            return theQuery.getResultList();
        }

        theQuery = currentSession.createQuery("from Customer", Customer.class);

        return theQuery.getResultList();
    }

    @Override
    public void deleteCustomer(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Customer> theQuery = currentSession.createQuery("delete from Customer where id = :id");
        theQuery.setParameter("id", id);
        theQuery.executeUpdate();
    }
}
