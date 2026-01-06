package praktikum;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    private Bun bun;
    // "white bun", 200
    @Mock
    private Ingredient sauceIngredient;
    // IngredientType.SAUCE, "chili sauce", 300
    @Mock
    private Ingredient fillingIngredient;
    // IngredientType.FILLING, "cutlet", 100)


    //@Parameterized.Parameter(0)
//    public IngredientType type;
//    //@Parameterized.Parameter(1)
//    public String name;
//    //@Parameterized.Parameter(2)
//    public float price;

    Burger burger;

//    public BurgerTest(){};
//    public BurgerTest(IngredientType type, String name, float price) {
//        this.type = type;
//        this.name = name;
//        this.price = price;
//    }

//    @Parameterized.Parameters
//    public static Object[][] testIngredients(){
//        return new Object[][]{
//                {IngredientType.SAUCE, "hot sauce", 100},
//                {IngredientType.SAUCE, "sour cream", 200},
//                {IngredientType.SAUCE, "chili sauce", 300},
//                {IngredientType.FILLING, "cutlet", 100},
//                {IngredientType.FILLING, "dinosaur", 200},
//                {IngredientType.FILLING, "sausage", 300},
//        };
//    }


    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        AutoCloseable autoCloseable = openMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBuns() {
        burger.setBuns(bun);
        assertEquals("Ошибка, булочки выбраны неправильно(setBuns)", bun, burger.bun);
    }

    @Test
    public void addIngredient() {
        burger.addIngredient(fillingIngredient);
        assertFalse("Ошибка, ингредиенты не добавлены (addIngredient)", burger.ingredients.isEmpty());
    }

    @Test
    public void removeIngredient() {
        burger.addIngredient(sauceIngredient);
        burger.removeIngredient(0);
        assertTrue("Ошибка, ингредиент не удален (removeIngredient)", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredient() {
        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);
        burger.moveIngredient(0, 1);

        int expectedIndex = 1;
        int actualIndex = burger.ingredients.indexOf(sauceIngredient);
        assertEquals("Ошибка, ингредиент не переместился (moveIngredient)", expectedIndex, actualIndex);
    }

    @Test
    public void getPrice() {
        burger.setBuns(bun);

        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);

        Mockito.when(bun.getPrice()).thenReturn((float)200);
        Mockito.when(sauceIngredient.getPrice()).thenReturn((float)300);
        Mockito.when(fillingIngredient.getPrice()).thenReturn((float)100);

        assertEquals(800.0, burger.getPrice(), 0.001);
    }

    @Test
    public void getReceipt() {
        burger.setBuns(bun);
        String result = String.format("(==== white bun ====)%n"
                + "= sauce chili sauce =%n"
                + "= filling cutlet =%n"
                + "(==== white bun ====)%n%n"
                + "Price: 800,000000%n");

        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);

        // "white bun", 200
        // IngredientType.SAUCE, "chili sauce", 300
        // IngredientType.FILLING, "cutlet", 100)

        Mockito.when(bun.getPrice()).thenReturn((float)200);
        Mockito.when(bun.getName()).thenReturn("white bun");
        Mockito.when(sauceIngredient.getPrice()).thenReturn((float)300);
        Mockito.when(sauceIngredient.getName()).thenReturn("chili sauce");
        Mockito.when(sauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(fillingIngredient.getPrice()).thenReturn((float)100);
        Mockito.when(fillingIngredient.getName()).thenReturn("cutlet");
        Mockito.when(fillingIngredient.getType()).thenReturn(IngredientType.FILLING);

        assertEquals(result, burger.getReceipt());
    }
}