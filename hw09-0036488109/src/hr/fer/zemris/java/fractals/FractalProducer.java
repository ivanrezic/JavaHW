package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * <code>FractalProducer</code> represents class which produces fractals from
 * given arguments. Instance of this class is provided as argument to
 * {@linkplain FractalViewer} so it could be show on content pane.
 *
 * @author Ivan Rezic
 */
public class FractalProducer implements IFractalProducer {

	/** Maximal number of iterations. */
	private static final int MAX_NUMBER_OF_ITERATIONS = 16 * 16;

	/** {@linkplain ComplexPolynomial} */
	private ComplexPolynomial polyinom;

	/** {@linkplain ComplexRootedPolynomial} */
	private ComplexRootedPolynomial rooted;

	/**
	 * Constructor which instantiates new fractal producer.
	 *
	 * @param polyinom
	 *            the polyinom
	 * @param rooted
	 *            the rooted
	 */
	public FractalProducer(ComplexPolynomial polyinom, ComplexRootedPolynomial rooted) {
		this.polyinom = polyinom;
		this.rooted = rooted;
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer) {
		System.out.println("Calculations started...");
		int m = MAX_NUMBER_OF_ITERATIONS;
		short[] data = new short[width * height];
		final int numberOfFileds = 8 * Runtime.getRuntime().availableProcessors();
		int numberOfYPerField = height / numberOfFileds;

		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (r) ->{
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		});
		List<Future<Void>> results = new ArrayList<>();

		for (int i = 0; i < numberOfFileds; i++) {
			int yMin = i * numberOfYPerField;
			int yMax = (i + 1) * numberOfYPerField;
			if (i == numberOfFileds - 1) {
				yMax = height - 1;
			}
			Calculated work = new Calculated(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
					yMin * width);
			results.add(pool.submit(work));
		}
		for (Future<Void> posao : results) {
			try {
				posao.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		System.out.println("Calculation done. Notifying observer ie. GUI!");
		observer.acceptResult(data, (short) (polyinom.order() + 1), requestNo);
	}

	/**
	 * <code>Calculated</code> is inner class which encapsulates all
	 * calculations done per iteration.
	 *
	 * @author Ivan Rezic
	 */
	public class Calculated implements Callable<Void> {

		/** Minimal real part of complex number. */
		double reMin;

		/** Maximal real part of complex number. */
		double reMax;

		/** Minimal imaginary part of complex number. */
		double imMin;

		/** Maximal imaginary part of complex number. */
		double imMax;

		/** Width of content pane. */
		int width;

		/** Height of content pane. */
		int height;

		/** Starting line of content to generate. */
		int yMin;

		/** Finishin line of content to generate. */
		int yMax;

		/** Max number of iterations. */
		int m;

		/** Array containing color indexes. */
		short[] data;

		/** Offset used in data. */
		int offset;

		/**
		 * Constructor which instantiates new calculated.
		 *
		 * @param reMin
		 *            {@link #reMin}
		 * @param reMax
		 *            {@link #reMax}
		 * @param imMin
		 *            {@link #imMin}
		 * @param imMax
		 *            {@link #imMax}
		 * @param width
		 *            {@link #width}
		 * @param height
		 *            {@link #height}
		 * @param yMin
		 *            {@link #yMin}
		 * @param yMax
		 *            {@link #yMax}
		 * @param m
		 *            {@link #m}
		 * @param data
		 *            {@link #data}
		 * @param offset
		 *            {@link #offset}
		 */
		public Calculated(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, int offset) {
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
}
