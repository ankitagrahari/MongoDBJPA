package com.dynamicallyblunttech.mongodbjpa.repo;

import com.dynamicallyblunttech.mongodbjpa.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {

    @Query("{name:'?0'}")
    List<Employee> findEmployeeByName(String name);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<Employee> findEmployeeByRegexpName(String regexp);

//    @Query(value = "{experience:'?0'}", fields = "{'name':1, 'address':1}")
    List<Employee> findEmployeeByExperienceEquals(int exp);

    @Query("{'experience': { $gt: ?0, $lt: ?1 } }")
    List<Employee> findEmployeesByExperienceBetween(int start, int end);

    @Override
    long count();

}
