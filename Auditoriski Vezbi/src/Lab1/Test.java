package Lab1;

import java.util.Arrays;
import java.util.Scanner;


//вашиот код

public class Test {


    public static void main(String[] args) {


        int n;
        Scanner in=new Scanner(System.in);
        n=in.nextInt();

        Patuvanje[] nizaPatuvanje = new Patuvanje[n];

        for (int i=0;i<n;i++){
            int tip=in.nextInt();
            if (tip==0){
                String ime=in.next();
                int cena =in.nextInt();
                int vreme=in.nextInt();
                nizaPatuvanje[i]=new GodishenOdmor(ime,cena,vreme);
            }
            else {
                String ime=in.next();
                int cena =in.nextInt();
                int pocD=in.nextInt();
                int pocM=in.nextInt();
                int krajD=in.nextInt();
                int krajM=in.nextInt();
                nizaPatuvanje[i]=new PraznicnoPatuvanje(ime,cena,pocD,pocM, krajD,krajM);

            }
        }

        //решение на барање 1
        for(Patuvanje p : nizaPatuvanje){
            if(p.getClass().toString().substring(6).equals("PraznicnoPatuvanje")) {
                PraznicnoPatuvanje patuvanje = (PraznicnoPatuvanje) p;
                if(patuvanje.getPocM() == 6)
                    System.out.print(patuvanje.getIme() + " ");
            }
        }

        //решение на барање 2
        System.out.println();
        double prosek = Arrays.stream(nizaPatuvanje).mapToInt(patuvanje -> patuvanje.vratiVremeVoDenovi()).average().getAsDouble();
        System.out.println(prosek);


        //решение на барање 3
        String ime = in.next();
        int cena = in.nextInt();
        int vreme = in.nextInt();

        GodishenOdmor odmor = new GodishenOdmor(ime, cena, vreme);

        //решение на барање 4
        int minCena = Patuvanje.vratiMinCena(nizaPatuvanje,nizaPatuvanje.length,odmor);
        System.out.print(minCena);




    }

}


abstract class Patuvanje {
    String ime;
    int cena;

    public Patuvanje() {}

    public Patuvanje(String ime, int cena) {
        this.ime = ime;
        this.cena = cena;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public static int vratiMinCena(Patuvanje[] niza, int n, Patuvanje zaSporedba) {
        return Arrays.stream(niza).filter(patuvanje -> patuvanje.vratiVremeVoDenovi() > zaSporedba.vratiVremeVoDenovi()).mapToInt(patuvanje->patuvanje.getCena()).min().orElse(0);
    }



    public abstract int vratiVremeVoDenovi();
}

class PraznicnoPatuvanje extends Patuvanje {
    int pocD;
    int pocM;
    int krajD;
    int krajM;

    public PraznicnoPatuvanje(String ime, int cena, int pocD, int pocM, int krajD, int krajM) {
        super(ime, cena);

        try {
            if (pocM > krajM || (pocM == krajM && pocD > krajD))
                throw new Exception("Iskluchok");

            this.pocD = pocD;
            this.pocM = pocM;
            this.krajD = krajD;
            this.krajM = krajM;

        } catch (Exception e) {
            System.out.println(e.getMessage());

//  change of values
            int temp = pocM;
            this.pocM = krajM;
            this.krajM = temp;

            temp = pocD;
            this.pocD = krajD;
            this.krajD = temp;

        }
    }

        public int getPocD () {
            return pocD;
        }

        public int getPocM () {
            return pocM;
        }

        public int getKrajD () {
            return krajD;
        }

        public int getKrajM () {
            return krajM;
        }

        @Override
        public int vratiVremeVoDenovi () {
            return 30 * ((krajM - pocM) - 1) + (30 - pocD) + krajD;
        }
    }



class GodishenOdmor extends Patuvanje {
    int vreme;

    public GodishenOdmor(String ime, int cena, int vreme) {
        super(ime, cena);
        this.vreme = vreme;
    }

    public int getVreme() {
        return vreme;
    }

    public void setVreme(int vreme) {
        this.vreme = vreme;
    }

    @Override
    public int getCena() {
        return super.getCena() - 1000;
    }

    @Override
    public int vratiVremeVoDenovi() {
        return vreme;
    }
}




