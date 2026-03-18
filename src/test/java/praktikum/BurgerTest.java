package praktikum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsSetsCorrectBun() {
        burger.setBuns(bun);
        Assert.assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientAddsToList() {
        burger.addIngredient(sauce);
        Assert.assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientReducesListSize() {
        burger.addIngredient(sauce);
        burger.removeIngredient(0);
        Assert.assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientChangesOrder() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.moveIngredient(0, 1);
        Assert.assertEquals(filling, burger.ingredients.get(0));
    }

    @Test
    public void getPriceCalculatesCorrectTotal() {
        Mockito.when(bun.getPrice()).thenReturn(100f);
        Mockito.when(sauce.getPrice()).thenReturn(50f);
        burger.setBuns(bun);
        burger.addIngredient(sauce);

        Assert.assertEquals(250f, burger.getPrice(), 0.0f);
    }

    @Test
    public void getReceiptContainsBunName() {
        Mockito.when(bun.getName()).thenReturn("Космическая булка");
        burger.setBuns(bun);
        Assert.assertTrue(burger.getReceipt().contains("Космическая булка"));
    }

    @Test // исправления здесь
    public void getReceiptReturnsValidValue() {
        Mockito.when(bun.getName()).thenReturn("Космическая булка");
        Mockito.when(bun.getPrice()).thenReturn(100f);
        Mockito.when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(sauce.getName()).thenReturn("Марсианский соус");
        Mockito.when(sauce.getPrice()).thenReturn(50f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);

        StringBuilder expected = new StringBuilder();
        expected.append("(==== Космическая булка ====)").append(System.lineSeparator());
        expected.append("= sauce Марсианский соус =").append(System.lineSeparator());
        expected.append("(==== Космическая булка ====)").append(System.lineSeparator());
        expected.append(System.lineSeparator());
        expected.append(String.format("Price: %f%n", 250.0f));

        Assert.assertEquals("Текст чека не совпадает с ожидаемым!",
                expected.toString(), burger.getReceipt());
    }

    @Test
    public void getReceiptContainsCorrectPrice() {
        Mockito.when(bun.getName()).thenReturn("Космическая булка");
        Mockito.when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Assert.assertTrue(burger.getReceipt().contains("Price: 200,000000"));
    }
}