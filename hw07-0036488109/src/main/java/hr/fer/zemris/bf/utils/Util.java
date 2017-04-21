package hr.fer.zemris.bf.utils;

import java.util.List;
import java.util.function.Consumer;

public class Util {

	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		int count = variables.size();
		int rows = (int) Math.pow(2,count);
		boolean[] array = new boolean[count];

        for (int i=0; i<rows; i++) {
            for (int j=count-1,k = 0; j>=0; j--) {
                if ((i/(int) Math.pow(2, j))%2 == 0) {
					array[k++] = false;
				}else {
					array[k++] = true;
				} 
            }
            consumer.accept(array);
        }
        
        
	}
}
