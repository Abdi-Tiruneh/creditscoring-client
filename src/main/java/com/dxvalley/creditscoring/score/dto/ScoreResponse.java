package com.dxvalley.creditscoring.score.dto;

import lombok.Data;

@Data
public class ScoreResponse {
    private Integer creditScore;
    public ModelDescription modelDescription;
}

@Data
class ModelDescription{
    public int Average_inflow;
    public int Average_outflow;
    public int Avg_Inflow_Count;
    public int Avg_Outflow_Count;
    public int Avg_Retained;
    public int Avg_Transaction_Count;
    public int Avg_Transaction_Count_1;
    public int Avg_retained_ratio;
    public int Inf_to_Outf_Ratio;
    public int Inf_txn_value_range;
    public int Inflow_parameter;
    public int Maximum_inflow;
    public int Maximum_ouflow;
    public int Minimum_inflow;
    public int Minimum_ouflow; 
    public int Number_of_txn_inflow;
    public int Number_of_txn_ouflow;
    public int Outf_txn_value_range;
    public int Outflow_parameter;
    public int Retained;
    public int Total_inflow;
    public int Total_ouflow;
    public int Transaction_count_ratio;
    public int Transaction_frequency;
}
