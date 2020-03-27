package com.github.hardelele.cca.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "params")
public class CreditParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double percent;
}
