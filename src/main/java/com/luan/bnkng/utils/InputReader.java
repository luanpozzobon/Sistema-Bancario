package com.luan.bnkng.utils;

import java.util.Scanner;

/**
 *
 * @author luanp
 */
public class InputReader {
    private Scanner sc;
    
    public InputReader(){
        sc = new Scanner(System.in);
    }
    
    public int getNextInt(){
        return Integer.parseInt(sc.nextLine());
    }
    
    public String getNextLine(){
        return sc.nextLine();
    }
}
