package com.adam.evaluaretehnica.badge;

import com.adam.evaluaretehnica.util.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Badge {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> cssStyles;
    private int importance;
    private String conditionDescription;
    public static final String condition = "";
}
