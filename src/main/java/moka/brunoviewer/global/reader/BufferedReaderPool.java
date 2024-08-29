package moka.brunoviewer.global.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * file read시 overhead를 줄이기 위한 BufferedReader Pool
 */
public class BufferedReaderPool {
	private final BlockingQueue<BufferedReader> pool;
	private final int poolSize;

	public BufferedReaderPool(int poolSize) {
		this.poolSize = poolSize;
		this.pool = new ArrayBlockingQueue<>(poolSize);
	}

	// Initialize the pool with BufferedReaders
	public void initializePool(Path filePath) throws IOException {
		for (int i = 0; i < poolSize; i++) {
			pool.offer(Files.newBufferedReader(filePath));
		}
	}

	// Acquire a BufferedReader from the pool
	public BufferedReader acquireBufferedReader() throws InterruptedException {
		return pool.take();
	}

	// Release a BufferedReader back to the pool
	public void releaseBufferedReader(BufferedReader bufferedReader) throws IOException {
		bufferedReader.reset(); // Reset the reader for reuse
		pool.offer(bufferedReader);
	}

	// Close all BufferedReaders in the pool
	public void closeAll() throws IOException {
		while (!pool.isEmpty()) {
			BufferedReader bufferedReader = pool.poll();
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}
}
