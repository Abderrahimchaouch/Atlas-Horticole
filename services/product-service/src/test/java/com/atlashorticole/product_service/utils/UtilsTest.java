package com.atlashorticole.product_service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

public class UtilsTest {



    @Test
    public void testCheckParamSortValidCase(){
        String queryParms = "id,desc";
        Sort result = Utils.checkParamSort(queryParms);
        assertNotNull(result);
        assertEquals(1, result.stream().count());
        Sort.Order order = result.iterator().next();
        assertNotNull(order);
        assertEquals("id", order.getProperty());
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }
    @Test
    public void testCheckParamSortIllegalArgumentExceptionCase(){

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, ()->Utils.checkParamSort(null));
        assertTrue(illegalArgumentException.getMessage().contains("Invalid sort parameter: "));
        
        IllegalArgumentException illegalArgumentException1 = assertThrows(IllegalArgumentException.class, ()->Utils.checkParamSort("id desc"));
        assertTrue(illegalArgumentException1.getMessage().contains("Invalid sort parameter: "));

        IllegalArgumentException illegalArgumentException2 = assertThrows(IllegalArgumentException.class, ()->Utils.checkParamSort("id,"));
        assertTrue(illegalArgumentException2.getMessage().contains("Invalid sort format: id,"));
        
        IllegalArgumentException illegalArgumentException3 = assertThrows(IllegalArgumentException.class, ()->Utils.checkParamSort("exception,asc"));
        assertTrue(illegalArgumentException3.getMessage().contains("Sorting not allowed on field: exception"));

        IllegalArgumentException illegalArgumentException4 = assertThrows(IllegalArgumentException.class, ()->Utils.checkParamSort("name,exception"));
        assertTrue(illegalArgumentException4.getMessage().contains("Sort direction must be 'asc' or 'desc'"));
    }


    
}
