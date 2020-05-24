package com.github.barry.fsm.sample.impl;

import com.github.barry.fsm.sample.StatefulEntity;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @ClassName User
 * @Description User实体
 * @Author wangxuexing
 * @Date 2020/4/28 20:45
 * @Version 1.0
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Repository
@Component
public class User implements StatefulEntity<UserStatus, Long> {
    private Long id;
    private String name;
    private UserStatus status;

    @Override
    public Long getId() {
        return id;
    }
}
