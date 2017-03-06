package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;

import static org.junit.Assert.*;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;



public class UniqueNumbersTest {

	@Test
	public void dodajeElementeUStablo() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 45);
		glava = addNode(glava, 77);
		glava = addNode(glava, 77);
		glava = addNode(glava, 79);
		glava = addNode(glava, 35);
		
		assertEquals(glava.right.right.right.value, 79);
		assertEquals(glava.right.right.value, 77);
		assertEquals(glava.right.value, 76);
		assertEquals(glava.right.left.value, 45);
		assertEquals(glava.left.right.value, 35);
		
	}
	@Test
	public void brojiElementeUStablu() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 45);
		glava = addNode(glava, 77);
		glava = addNode(glava, 77);
		glava = addNode(glava, 79);
		glava = addNode(glava, 35);
		
		assertEquals(treeSize(glava),7);
		glava = addNode(glava, 77);
		assertEquals(treeSize(glava),7);
		glava = addNode(glava, 12);
		assertEquals(treeSize(glava),8);
		
	}
	@Test
	public void brojiElementeUPraznomStablu() {
		TreeNode glava = null;
		assertEquals(treeSize(glava),0);
		
	}
	
	@Test
	public void sadrziElementeUStablu() {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
		glava = addNode(glava, 45);
		glava = addNode(glava, 77);
		glava = addNode(glava, 77);
		glava = addNode(glava, 79);
		glava = addNode(glava, 35);
		
		assertEquals(containsValue(glava, 77),true);
		assertEquals(containsValue(glava, 79),true);
		assertEquals(containsValue(glava, 43),false);
		
	}
	
	@Test
	public void sadrziElementUPraznomStablu() {
		TreeNode glava = null;
		assertEquals(containsValue(glava, 25), false);

		
	}

}
