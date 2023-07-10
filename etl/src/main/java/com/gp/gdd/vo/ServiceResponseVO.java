package com.gp.gdd.vo;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
/**
 * This POJO is used as a response object to pass the response body 
 * and header information back to caller method during the gitlab API fetch operation
 */
public class ServiceResponseVO {

    /**
     * Current page No
     */
    int pageNo;

    /**
     * Total pages available in the API
     */
    int totalPages;

    /**
     * Current Json data from the API
     */
    JsonNode responseJson;

    
}
