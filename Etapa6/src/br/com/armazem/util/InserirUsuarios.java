package br.com.armazem.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class InserirUsuarios {
    public static void main(String[] args) {
        // Gerar hashes das senhas
        String senhaJoao = BCrypt.withDefaults().hashToString(12, "senha123".toCharArray());
        String senhaMaria = BCrypt.withDefaults().hashToString(12, "senha123".toCharArray());
        String senhaCarlos = BCrypt.withDefaults().hashToString(12, "senha123".toCharArray());
        String senhaAna = BCrypt.withDefaults().hashToString(12, "senha123".toCharArray());

        //Imprimir os hashes gerados 
        System.out.println("João: " + senhaJoao);
        System.out.println("Maria: " + senhaMaria);
        System.out.println("Carlos: " + senhaCarlos);
        System.out.println("Ana: " + senhaAna);
        
        /*Atuliazar manualmente a coluna "Senha_hashed" da tabela usuario
        com os hashes gerados.
        */
    }
}
