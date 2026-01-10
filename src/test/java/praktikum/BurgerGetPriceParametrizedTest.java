package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerGetPriceParametrizedTest {
    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient;

    Burger burger;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    public float bunPrice;
    int numberOfIngredients;
    IngredientType[] ingredientType;
    String[] ingredientName;
    float[] ingredientPrice;
    float burgerExpectedPrice;

    public BurgerGetPriceParametrizedTest(float bunPrice, int numberOfIngredients, IngredientType[] ingredientType,
                                          String[] ingredientName, float[] ingredientPrice,
                                          float burgerExpectedPrice) {

        this.bunPrice = bunPrice;
        this.numberOfIngredients = numberOfIngredients;
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
        this.burgerExpectedPrice = burgerExpectedPrice;
    }

    @Parameterized.Parameters(name = "{index}: bun={0}, numberOfIngredients={1}, burgerExpectedPrice={5}")
    public static Object[][] data() {
        return new Object[][]{
                {200F, 1, new IngredientType[] {IngredientType.FILLING} ,
                        new String[] {"dinosaur"}, new float[] {200F}, 600F},
                {100F, 2, new IngredientType[] {IngredientType.SAUCE, IngredientType.FILLING} ,
                        new String[] {"hot sauce", "dinosaur"}, new float[] {100F, 200F}, 500F},
                {100F, 3, new IngredientType[] {IngredientType.SAUCE, IngredientType.SAUCE, IngredientType.FILLING} ,
                        new String[] {"chili sauce", "hot sauce", "sausage"}, new float[] {300F, 100F, 300F}, 900F},
        };
    }

    @Test
    public void burgerGetPriceParamTest() {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(bun.getName()).thenReturn("test bun");
        burger.setBuns(bun);

        for (int i = 0; i < numberOfIngredients; i++) {
            Ingredient ingredient = mock(Ingredient.class);
            when(ingredient.getPrice()).thenReturn(ingredientPrice[i]);
            when(ingredient.getName()).thenReturn(ingredientName[i]);
            when(ingredient.getType()).thenReturn(ingredientType[i]);
            burger.addIngredient(ingredient);
        }

        assertEquals((int) burgerExpectedPrice, (int) burger.getPrice(), 0.001);
    }
}