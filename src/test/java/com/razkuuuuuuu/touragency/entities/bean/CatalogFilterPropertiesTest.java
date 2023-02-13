package com.razkuuuuuuu.touragency.entities.bean;

import com.razkuuuuuuu.touragency.exception.AppException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CatalogFilterPropertiesTest {


    @Test
    void testToStringWithNoTypesSelected() {
        CatalogFilterProperties properties = new CatalogFilterProperties();
        properties.setVacationSelected(false);
        properties.setExcursionSelected(false);
        properties.setShoppingSelected(false);
        properties.setSortCode(0);
        Assertions.assertEquals("filter={selected:none+sortCode:0}", properties.toString());
    }

    @Test
    void testToStringWithTwoSelected() {
        CatalogFilterProperties properties = new CatalogFilterProperties();
        properties.setVacationSelected(true);
        properties.setExcursionSelected(true);
        properties.setShoppingSelected(false);
        properties.setSortCode(0);
        Assertions.assertEquals("filter={selected:[vacation,excursion]+sortCode:0}", properties.toString());
    }

    @Test
    void testToStringWithAllSelected() {
        CatalogFilterProperties properties = new CatalogFilterProperties();
        properties.setVacationSelected(true);
        properties.setExcursionSelected(true);
        properties.setShoppingSelected(true);
        properties.setSortCode(0);
        Assertions.assertEquals("filter={selected:all+sortCode:0}", properties.toString());
    }

    @Test
    void testFromStringWithRandomTypesSelected() throws AppException {
        int sortCode = 0;

        Random random = new Random();
        boolean vacationRandom = random.nextBoolean();
        boolean excursionRandom = random.nextBoolean();
        boolean shoppingRandom = random.nextBoolean();

        CatalogFilterProperties properties = new CatalogFilterProperties();
        properties.setSortCode(sortCode);
        properties.setVacationSelected(vacationRandom);
        properties.setExcursionSelected(excursionRandom);
        properties.setShoppingSelected(shoppingRandom);

        assertEquals(properties, CatalogFilterProperties.fromString(properties.toString()));
    }
}