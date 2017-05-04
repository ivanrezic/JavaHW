package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class FractalProducer implements IFractalProducer {

	private ComplexPolynomial polyinom;
	private ComplexRootedPolynomial rooted;

	public FractalProducer(ComplexPolynomial polyinom, ComplexRootedPolynomial rooted) {
		super();
		this.polyinom = polyinom;
		this.rooted = rooted;
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer) {
		System.out.println("Zapocinjem izracun...");
		int m = 16*16;
		short[] data = new short[width * height];
		final int brojTraka = 8 * Runtime.getRuntime().availableProcessors();
		int brojYPoTraci = height / brojTraka;

		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<Future<Void>> rezultati = new ArrayList<>();

		for (int i = 0; i < brojTraka; i++) {
			int yMin = i * brojYPoTraci;
			int yMax = (i + 1) * brojYPoTraci - 1;
			if (i == brojTraka - 1) {
				yMax = height - 1;
			}
			Calculated posao = new Calculated(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
					yMin * width);
			rezultati.add(pool.submit(posao));
		}
		for (Future<Void> posao : rezultati) {
			try {
				posao.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		pool.shutdown();
		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short) (polyinom.order() + 1), requestNo);
	}

	public class Calculated implements Callable<Void> {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		int offset;

		public Calculated(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, int offset) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.offset = offset;
		}

		@Override
		public Void call() {

			for (int i = yMin; i < yMax; i++) {
				for (int j = 0; j < width; j++) {
					double real = j / (width - 1.0) * (reMax - reMin) + reMin;
					double imaginary = (height - 1.0 - i) / (height - 1) * (imMax - imMin) + imMin;

					Complex zn = new Complex(real, imaginary);
					Complex zn1;
					double module = 0;
					int iter = 0;
					do {
						Complex numerator = polyinom.apply(zn);
						Complex denominator = polyinom.derive().apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						iter++;

						module = zn1.sub(zn).module();
						zn = zn1;
					} while (module > 0.001 && iter < m);

					int index = rooted.indexOfClosestRootFor(zn1, 0.002);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) index;
					}
				}
			}
			return null;
		}
	}
	
	public static void main(String[] args) {
		ComplexRootedPolynomial roots = new ComplexRootedPolynomial(Complex.ONE,Complex.ONE_NEG,Complex.IM,Complex.IM_NEG);
		ComplexPolynomial poly = roots.toComplexPolynom();
		FractalProducer producer = new FractalProducer(poly, roots);
		
		FractalViewer.show(producer);
	}
}
