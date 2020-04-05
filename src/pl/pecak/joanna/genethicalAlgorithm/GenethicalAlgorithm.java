package pl.pecak.joanna.genethicalAlgorithm;

import java.io.*;

import java.util.Random;

import javax.sound.midi.SysexMessage;


public class GenethicalAlgorithm {
	static Random ran=new Random();
	static int[][] tablica;
	public static int[] losowyOsobnik(int n){
		int[] os = new int[n];
		for (int i=0; i<n; i++){
			os[i] = i;
		}

		shuffleArray(n, os);

		return os;

	}

	private static void shuffleArray(int n, int[] os) {
		for (int i=0; i<n; i++){
			int pos =ran.nextInt(n);
			int tmp =os[pos];

			os[pos]=os[i];
			os[i]=tmp;
		}
	}



	public static void main(String[] args) throws IOException {
		int n=0;
		int lOs = 20;
		int k = 2;
		try (BufferedReader plik = new BufferedReader(new FileReader("berlin52.txt"))){

			String l = plik.readLine().trim();
			n = Integer.parseInt(l);
			tablica = new int[n][n];
			System.out.println(n);

			l = plik.readLine();
			int nol = 0;
			while(l!=null){
				l = l.trim();

				String[] tmp = l.split(" ");

				for(int i=0; tmp.length > i; i++){
					tablica[nol][i] = Integer.parseInt(tmp[i]);
					tablica[i][nol] = tablica[nol][i];

				}
				l = plik.readLine();

				nol++;
			}
		}
			int[][] osobniki=new int[lOs][n];
			int[] ocena=new int[lOs];
			for (int i = 0; i < osobniki.length; i++) {
				osobniki[i]=losowyOsobnik(n);
				ocena[i] = odleglosc(osobniki[i]);
				//losuje osobnika i wpisuje do tablicy

			}

			int[][] osTmp=new int[lOs][n];
			// selekcjaTurniejowa;
			for (int[] value : osTmp) {
				int losowy = ran.nextInt(lOs);
				int max = losowy;

				for (int i = 1; i < k; i++) {
					losowy = ran.nextInt(lOs);
					if (ocena[max] > ocena[losowy])
						max = losowy;
				}
				System.arraycopy(osobniki[max], 0, value, 0, osobniki[max].length);

			}

			for (int[] ints : osTmp) {
				for (int anInt : ints) {
					System.out.print(anInt + " ");
				}
				System.out.println();
			}
		}

	private static int odleglosc(int[] os) {

		int dystans=0;

		for (int i = 0; i < os.length-1; i++) {

			dystans+=tablica[os[i]][os[i+1]];
		}
		dystans+= tablica[os.length-1][0];
		return dystans;
	}

//system ruletkowy
	//krzyżowanie
	private static int[][] krzyzowanie(int[][] pop) {
		int[][] popk=new int[pop.length][pop[0].length];
		int n = pop[0].length;

		for(int i=0; i<pop.length;i=i+2) {
			int pp = ran.nextInt(n-2)+1; //pierwszy punkt
			int dp = ran.nextInt(n-(pp+1))+pp+1; //drugi punkt http://podstawyprogramowania.pl/java/przyklady/java-losowanie-liczb/

			for(int j=pp; j<=dp; j++) { //środek
				popk[i][j]=pop[i+1][j];
				popk[i+1][j]=pop[i][j];
			}

			for(int j=0; j<pp; j++) { //początek
				int gen = pop[i][j];
				int indeks = sprawdzGen(gen, popk, i,pp, dp);
				while(indeks>-1) {
					gen = pop[i][indeks];
					indeks = sprawdzGen(gen, popk, i,pp, dp);
				}
				popk[i][j]=gen;

				gen = pop[i+1][j];
				indeks = sprawdzGen(gen, popk, i,pp, dp);
				while(indeks>-1) {
					gen = pop[i+1][indeks];
					indeks = sprawdzGen(gen, popk, i,pp, dp);
				}
				popk[i+1][j]=gen;
			}

			for(int j=dp+1; j<n; j++) { //koniec
				int gen = pop[i][j];
				int indeks = sprawdzGen(gen, popk, i,pp, dp);
				while(indeks>-1) {
					gen = pop[i][indeks];
					indeks = sprawdzGen(gen, popk, i,pp, dp);
				}
				popk[i][j]=gen;

				gen = pop[i+1][j];
				indeks = sprawdzGen(gen, popk, i,pp, dp);
				while(indeks>-1) {
					gen = pop[i+1][indeks];
					indeks = sprawdzGen(gen, popk, i,pp, dp);
				}
				popk[i+1][j]=gen;
			}

		}

		return popk;
	}

	private static int sprawdzGen(int gen,int[][] popk,int i,int pp,int dp) {
		for(int j=pp; j<=dp; j++) { //środek
			if(popk[i][j]==gen) {
				return j;
			}
		}
		return -1;
	}

	private static int[][] mutowanie (int[][] pop)
	{
		for(int i = 0; i<pop.length;i++) {
			for(int j=0; j< pop[i].length; j++) {

				if(ran.nextInt(1000)<5) {
					int losInd = ran.nextInt(pop[i].length);
					//zamiana
					int tmp = pop[i][j];
					pop[i][j]=pop[i][losInd];
					pop[i][losInd]=tmp;
				}
			}
		}

		return pop;
	}
}