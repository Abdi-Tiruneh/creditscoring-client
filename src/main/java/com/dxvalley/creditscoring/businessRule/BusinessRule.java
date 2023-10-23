package com.dxvalley.creditscoring.businessRule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_score", nullable = false)
    private Integer minScore;

    @Column(name = "max_score", nullable = false)
    private Integer maxScore;

    @Column(name = "amount_allowed", nullable = false)
    private Double amountAllowed;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    private String remark;

    private String xCode;
}
