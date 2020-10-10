/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * Modifications
 * 20171113 - Michael W. Whalen - Extended with additional recipe.
 * 20171114 - Ian J. De Silva   - Updated to JUnit 4; fixed variable names.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;



/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 *
 * Extended by Mike Whalen
 */

public class CoffeeMakerTest {
	
	//-----------------------------------------------------------------------
	//	DATA MEMBERS
	//-----------------------------------------------------------------------
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;
	
	private Recipe [] stubRecipies;  // version of recipes array 
	
	/**
	 * The coffee maker -- our object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	/**
	 * The stubbed recipe book.
	 */
	private RecipeBook recipeBookStub;
	
	
	
	//-----------------------------------------------------------------------
	//	Set-up / Tear-down
	//-----------------------------------------------------------------------
	/**
	 * Initializes some recipes to test with, creates the {@link CoffeeMaker} 
	 * object we wish to test, and stubs the {@link RecipeBook}. 
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		
		recipeBookStub = mock(RecipeBook.class);
		coffeeMaker = new CoffeeMaker(recipeBookStub, new Inventory());
		
		//Set up for recipe1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for recipe2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for recipe3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for recipe4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
		
		//Set up for recipe5 (added by MWW)
		recipe5 = new Recipe();
		recipe5.setName("Super Hot Chocolate");
		recipe5.setAmtChocolate("6");
		recipe5.setAmtCoffee("0");
		recipe5.setAmtMilk("1");
		recipe5.setAmtSugar("1");
		recipe5.setPrice("100");

		stubRecipies = new Recipe [] {recipe1, recipe2, recipe3};
		
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);	
		
		when(recipeBookStub.addRecipe(recipe1)).thenReturn(true);
		when(recipeBookStub.addRecipe(recipe2)).thenReturn(true);
		when(recipeBookStub.addRecipe(recipe3)).thenReturn(true);
		when(recipeBookStub.addRecipe(recipe4)).thenReturn(true);
		when(recipeBookStub.addRecipe(recipe5)).thenReturn(true);
	
		when(recipeBookStub.deleteRecipe(0)).thenReturn(recipe1.getName());
		when(recipeBookStub.deleteRecipe(1)).thenReturn(recipe2.getName());
		when(recipeBookStub.deleteRecipe(2)).thenReturn(recipe3.getName());
		when(recipeBookStub.deleteRecipe(3)).thenReturn(recipe4.getName());
		when(recipeBookStub.deleteRecipe(4)).thenReturn(recipe5.getName());
		
		when(recipeBookStub.editRecipe(0, recipe1)).thenReturn(recipe1.getName());
		when(recipeBookStub.editRecipe(1, recipe2)).thenReturn(recipe2.getName());
		when(recipeBookStub.editRecipe(2, recipe3)).thenReturn(recipe3.getName());
		when(recipeBookStub.editRecipe(3, recipe4)).thenReturn(recipe4.getName());
		when(recipeBookStub.editRecipe(4, recipe5)).thenReturn(recipe5.getName());
		
	}
	    

	//-----------------------------------------------------------------------
	//	Test Methods
	//-----------------------------------------------------------------------
	
	// put your tests here!
	
	@Test
	public void testMakeCoffee() {
		coffeeMaker = new CoffeeMaker(recipeBookStub, new Inventory());
		assertTrue(true);
	}
	
	/**
	 * given a coffee maker with a valid recipe
	 * order this recipe and pay amount more than its price
	 */
	@Test
	public void testMakeCoffee1() {
		coffeeMaker = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(0, coffeeMaker.makeCoffee(0, 50));
	}
	
	/**
	 * given a coffee maker with a valid recipe 
	 * order this recipe and pay amount exactly equal to its price
	 */
	@Test
	public void testMakeCoffee3() {	
		assertEquals(65, coffeeMaker.makeCoffee(2, 65));
	}
	
	@Test
	public void testMakeCoffee4() {	
		assertEquals(65, coffeeMaker.makeCoffee(4, 65));
	}
	
	/**
	 * given a coffee maker with a valid recipe
	 * order this recipe and pay amount less than its price
	 */
	@Test
	public void testInvalidPurchase2() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(10, coffeeMaker.makeCoffee(0, 10));
	}
	
	/**
	 * given a coffee maker with no recipes in it 
	 * try to order a recipe 
	 */
	@Test
	public void testInvalidPurchase1() {
		assertEquals(50, coffeeMaker.makeCoffee(0, 100));
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quantity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
	}	
	@Test 
	public void testaddSugar3() throws InventoryException {
		Inventory I = new Inventory();
		try {
		I.addSugar("-1");
		}
		catch (Exception e) {
			// TODO: handle exception
		assertEquals(15, I.getSugar());
		}
	}
	@Test 
	public void testaddSugar4() throws InventoryException {
		Inventory I = new Inventory();
		try {
			I.addSugar("-1");
			}
			catch (Exception e) {
				// TODO: handle exception
			assertEquals(15, I.getSugar());
			}
	}
	
	/**
	 * given a working coffee machine
	 * add Chocolate to the inventories 
	 * 4 test cases to test all internal functions used to add Chocolate  
	 */
	@Test 
	public void testaddchocolate1() throws InventoryException {
		coffeeMaker.addInventory("0", "0", "0", "5");
		coffeeMaker.checkInventory();
		Inventory I = new Inventory();
		assertEquals(15, I.getChocolate());
	}
	@Test 
	public void testaddchocolate2() throws InventoryException {
		Inventory I = new Inventory();
		I.addChocolate("5");
		assertEquals(20, I.getChocolate());
	}
	@Test 
	public void testaddchocolate3() throws InventoryException {
		Inventory I = new Inventory();
		try {
		I.addChocolate("-1");
		}
		catch (Exception e) {
			// TODO: handle exception
		assertEquals(15, I.getChocolate());
		}
	}
	@Test 
	public void testaddchocolate4() throws InventoryException {
		Inventory I = new Inventory();
		try {
			I.addChocolate("-1");
			}
			catch (Exception e) {
				// TODO: handle exception
			assertEquals(15, I.getChocolate());
			}
	}
	
	/**
	 * given a working coffee machine
	 * add milk to the inventories 
	 * 4 test cases to test all internal functions used to add milk  
	 */
	@Test 
	public void testAddMilk1() throws InventoryException {
		Inventory I = new Inventory();
		coffeeMaker.addInventory("0", "5", "0", "0");		
		assertEquals(20, I.getMilk());
	}
	@Test 
	public void testAddMilk2() throws InventoryException {
		Inventory I = new Inventory();
		I.addMilk("5");
		assertEquals(20, I.getMilk());
	}
	@Test 
	public void testAddMilk3() throws InventoryException {
		Inventory I = new Inventory();
		try {
		I.addMilk("-100");
		}
		catch (Exception e) {
			// TODO: handle exception
		assertEquals(15, I.getMilk());
		}
	}
	@Test 
	public void testAddMilk4() throws InventoryException {
		Inventory I = new Inventory();
		try {
			I.addMilk("-100");
			}
			catch (Exception e) {
				// TODO: handle exception
			assertEquals(15, I.getMilk());
			}
	}
	
	/**
	 * given a working coffee machine
	 * add coffee to the inventories 
	 * 4 test cases to test all internal functions used to add coffee
	 */
	@Test 
	public void testAddcoffe1() throws InventoryException {
		coffeeMaker.addInventory("5", "0", "0", "0");
		Inventory I = new Inventory();
		assertEquals(15, I.getCoffee());
	}
	@Test 
	public void testAddcoffe2() throws InventoryException {
		Inventory I = new Inventory();
		I.addCoffee("5");
		assertEquals(20, I.getCoffee());
	}
	@Test 
	public void testAddcoffe3() throws InventoryException {
		Inventory I = new Inventory();
		try {
		I.addCoffee("-9");
		}
		catch (Exception e) {
			// TODO: handle exception
		assertEquals(15, I.getCoffee());
		}
	}
	@Test 
	public void testAddcoffe4() throws InventoryException {
		Inventory I = new Inventory();
		try {
			I.addCoffee("-9");
			}
			catch (Exception e) {
			assertEquals(15, I.getCoffee());
			}
	}
	
	/*
	 * testing set coffee function
	 */
	@Test
	public void testSetCoffee() {
		Inventory I = new Inventory();
		I.setCoffee(-9);
		assertEquals(15, I.getCoffee());
	}
	@Test
	public void testSetAmtCoffee() throws RecipeException {
		Recipe R = new Recipe();
		try {
		R.setAmtCoffee("asd");
		}
		catch (Exception e) {
		assertEquals(0, R.getAmtCoffee());
		}
	}

	/*
	 * testing set milk function
	 */
	@Test
	public void testSetMilk() {
		Inventory I = new Inventory();
		I.setMilk(-5);
		assertEquals(15, I.getMilk());
	}
	@Test
	public void testSetAmtMilk() throws RecipeException {
		Recipe R = new Recipe();
		try {
		R.setAmtMilk("asd");
		}
		catch (Exception e) {
			// TODO: handle exception
		assertEquals(0, R.getAmtMilk());
		}
	}
	
	/*
	 * testing set sugar function
	 */
	@Test
	public void testSetSugar() {
		Inventory I = new Inventory();
		I.setSugar(-100);
		assertEquals(15, I.getSugar());
	}
	
	/*
	 * testing set chocolate function
	 */
	@Test
	public void testSetChocolate() {
		Inventory I = new Inventory();
		I.setChocolate(-50);
		assertEquals(15, I.getChocolate());
	}
	
	@Test
	public void testCheckInventory() {
		coffeeMaker.makeCoffee(0, 50);
		coffeeMaker.makeCoffee(0, 50);
		coffeeMaker.makeCoffee(0, 50);
		coffeeMaker.makeCoffee(0, 50);
		coffeeMaker.makeCoffee(0, 50);
		assertEquals(50, coffeeMaker.makeCoffee(0, 50));
		
	}


}
