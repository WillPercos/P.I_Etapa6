package br.com.armazem.service;

import br.com.armazem.model.Item;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemServiceTest {
    
    public ItemServiceTest() {
    }

    /**
     * Teste do método isAbaixoDoPontoDeRessuprimento, da classe ItemService.
     */
    @Test
    public void testIsAbaixoDoPontoDeRessuprimento_DeveRetornarTrue() {
        System.out.println("Testando item abaixo do ponto de ressuprimento");
        
        // Criando um item de teste onde a quantidade é menor que o ponto de ressuprimento
        Item item = new Item(1, "Semente de Milho", 10, "2023-01-01", "2024-01-01", "LoteA", 15);
        ItemService instance = new ItemService();
        
        boolean expResult = true;
        boolean result = instance.isAbaixoDoPontoDeRessuprimento(item);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testIsAbaixoDoPontoDeRessuprimento_DeveRetornarFalse() {
        System.out.println("Testando item acima do ponto de ressuprimento");
        
        // Criando um item de teste onde a quantidade é maior ou igual ao ponto de ressuprimento
        Item item = new Item(2, "Fertilizante", 50, "2023-02-01", "2024-02-01", "LoteB", 40);
        ItemService instance = new ItemService();
        
        boolean expResult = false;
        boolean result = instance.isAbaixoDoPontoDeRessuprimento(item);
        
        assertEquals(expResult, result);
    }
}
