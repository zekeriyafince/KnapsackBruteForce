
package knapsack;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class BruteForce
{
    ArrayList<EsyaItem> items = new ArrayList<EsyaItem>();
    int maxWeight ;
    double processTime;

    public BruteForce(int maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    
    
    public void ReadToFileTxt()  //Dosyadan okuma yapan method.
    {
        items.clear();
        String workingDir = System.getProperty("user.dir");
        String filePath = workingDir + "\\src\\knapsack\\canta.txt";
        String read="";
        if(filePath != null)
        {
            try {
                BufferedReader fr = new BufferedReader(new FileReader(filePath));
                String readLine="";
                while( (readLine = fr.readLine()) != null )
                {
                    SplitLineToAddList(readLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //
        BruteForceFunction();
    }
    
    ///dosyadaki her bir satırın parcalanması ve liste eklenmesi
    private void SplitLineToAddList(String read)
    {
        String[] splitRead = read.split(" ");
        int size = splitRead.length;
        EsyaItem temp = new EsyaItem();
        temp.esyaValue = Integer.parseInt(splitRead[--size]);
        temp.esyaWeight = Integer.parseInt(splitRead[--size]);
        for(int i = 0; i<size; i++)
        {
            temp.esyaName = splitRead[i] + " ";
        }
        items.add(temp);
    }
    
    // esya item list print
    private void printArray()
    {
        int i = 0;
        for(EsyaItem ci : items)
        {
            i++;
            System.out.print(i + " " + ci.esyaName + " " + ci.esyaWeight + " " + ci.esyaValue);
            System.out.println();
        }
    }

    private void BruteForceFunction()
    {
        long startTime = System.currentTimeMillis();
        // eklenen eski itemleri tutar
        ArrayList<String> lastItems = new ArrayList<String>();
        // eklendikten sonraki ile yeni itemleri ekler.
        ArrayList<String> neuItems = new ArrayList<String>();
        String optFindItem="";
        int optValue=0;
        int optWeight = 0;
        int tempValue, tempWeight;
        int sizeItem = items.size();
        
        // initialize olarak itemler full eklenir.
        for(int i = 0; i<sizeItem; i++) {
            lastItems.add(String.valueOf(i));
        }
        // Tum esya boyunca dongu olacak.
        for(int i = 1; i<=sizeItem; i++)
        {
            String str="";
            for(int j = 0; j<lastItems.size(); j++)
            {
                String[] lstItem = lastItems.get(j).split(",");
                // son itemden sonrakini almaya baslicak indisteki
                int k = Integer.parseInt(lstItem[lstItem.length - 1])+1;
                for(; k<sizeItem; k++)
                {
                    str = String.valueOf(lastItems.get(j))+","+k;
                    neuItems.add(str);
                    // alınan itemlerin degerlerin hesaplanması ve optimumun alınması
                    int[] vw = calculateThis(str);
                    if(vw[1] <= maxWeight && vw[0] > optValue)
                    {
                        optValue = vw[0];
                        optWeight = vw[1];
                        optFindItem = str;
                    }
                }
            }
            lastItems.clear();
            //eklenen itemlerın son halini last atacak ki burdan devam edecek
            for(int t = 0; t<neuItems.size(); t++) {
                lastItems.add(neuItems.get(t));
            }
            neuItems.clear();
        }
        long endTime = System.currentTimeMillis();
        processTime = (endTime - startTime)/1000.0;
        printResult(optValue, optWeight, optFindItem);
    }
    
    // Alinacak itemlerin total weight ve total value hesaplanır.
    private int[] calculateThis(String str)
    {
        int[] arrayVW = new int[2];
        String[] in = str.split(",");
        arrayVW[0] = 0;
        arrayVW[1] = 0;
        for(int i = 0; i<in.length; i++)
        {
            arrayVW[0] += items.get(Integer.parseInt(in[i])).esyaValue;
            arrayVW[1] += items.get(Integer.parseInt(in[i])).esyaWeight;
        }
        return arrayVW;
    }
    // Formatlı cıktı   
    private void printResult(int optValue, int opWeight, String item)
    {
        System.out.println("Optimum Result : ");
        System.out.println("Weight :" + opWeight + "*, Value : " + optValue);
        System.out.println("*Max. Weight : " + maxWeight);
        System.out.println("Operation Time : " + processTime + " seconds");
        int maxName = 0, maxW = 0, maxV = 0;
        int i = 0;
        String[] itm = item.split(",");
        for(i = 0; i<itm.length; i++)
        {
            if(items.get(Integer.parseInt(itm[i])).esyaName.length() > maxName)
                maxName = items.get(Integer.parseInt(itm[i])).esyaName.length();
            if(String.valueOf(items.get(Integer.parseInt(itm[i])).esyaWeight).length() > maxW)
                maxW = String.valueOf(items.get(Integer.parseInt(itm[i])).esyaWeight).length();
            if(String.valueOf(items.get(Integer.parseInt(itm[i])).esyaValue).length() > maxV)
                maxV = String.valueOf(items.get(Integer.parseInt(itm[i])).esyaValue).length();
        }

        System.out.println();
        System.out.print("Item");
        i = 3;
        while(i<maxName)
        {
            System.out.print(" ");
            i++;
        }
        System.out.println(" Weight  Value");
        System.out.println("------------------------");
        for(int j = 0; j<itm.length; j++)
        {
            System.out.print(items.get(Integer.parseInt(itm[j])).esyaName);
            i = items.get(Integer.parseInt(itm[j])).esyaName.length();
            while(i<maxName)
            {
                System.out.print(" ");
                i++;
            }
            System.out.print("   ");
            i = String.valueOf(items.get(Integer.parseInt(itm[j])).esyaWeight).length();
            while(i<maxW)
            {
                System.out.print(" ");
                i++;
            }
            System.out.print(items.get(Integer.parseInt(itm[j])).esyaWeight);
            i = String.valueOf(items.get(Integer.parseInt(itm[j])).esyaValue).length();
            while(i<maxV)
            {
                System.out.print(" ");
                i++;
            }
            System.out.print("    ");
            System.out.print(items.get(Integer.parseInt(itm[j])).esyaValue);
            System.out.println();
        }

    }
}
