package com.nhn.mongo;

import com.nhn.webflux.reactive.user.entity.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author haekyu cho
 */
public interface UserMongoReactiveRepository extends ReactiveCrudRepository<User, Long> {}
