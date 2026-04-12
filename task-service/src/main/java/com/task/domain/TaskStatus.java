package com.task.domain;

import lombok.NoArgsConstructor;


public enum TaskStatus {
    PENDING("PENDING"),
    ASSIGNED("ASSIGNED"),
    DONE("DONE");

    TaskStatus(String done){

    }
}
