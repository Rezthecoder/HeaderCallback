package com.suresh.springbatch.config;

import com.suresh.springbatch.model.User;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

  @Override
  public User process(User user) throws Exception {
    return user;
  }
}
