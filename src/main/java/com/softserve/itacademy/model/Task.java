package com.softserve.itacademy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "priority")
@EqualsAndHashCode(of = "name")
public class Task {

    private String name;
    private Priority priority;

}
