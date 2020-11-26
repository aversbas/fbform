package com.fbform.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "form")
@Data
@NoArgsConstructor
public class Feedback {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "contact_text")
    private String contactText;

    @Column(name = "mail")
    private String mail;
}
