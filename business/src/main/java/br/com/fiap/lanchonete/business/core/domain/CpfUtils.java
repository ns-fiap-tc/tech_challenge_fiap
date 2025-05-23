package br.com.fiap.lanchonete.business.core.domain;

import java.util.Objects;

public class CpfUtils {

    private static final int[] PESO = {11,10,9,8,7,6,5,4,3,2};

    public static boolean isValid(String s) {
        if(Objects.isNull(s) || s.length() != 11) return false;
        return isValidCpf(s);
    }

    private static boolean isValidCpf(String cpf){
        for (int i = 0; i < 10; i++)
            if(padLeft(Integer.toString(i), Character.forDigit(i,10)).equals(cpf)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9),PESO);
        Integer digito2 = calcularDigito(cpf.substring(0,9)+ digito1,PESO);

        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    private static String padLeft(String texto, char caracter){
        return String.format("%11s",texto).replace(' ',caracter);
    }

    private static int calcularDigito(String str, int[] peso){
        int soma = 0;
        for (int indice = str.length() -1, digito; indice >=0; indice --){
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }
}